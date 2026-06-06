package org.example.msteacher.modules.teacher.application_usecase;

import feign.FeignException;
import jakarta.transaction.Transactional;
import org.example.msteacher.clients.academic.SchoolClient;
import org.example.msteacher.clients.academic.dto.SchoolValidationResponse;
import org.example.msteacher.clients.security.SecurityClient;
import org.example.msteacher.clients.security.dto.CreateUserRequest;
import org.example.msteacher.clients.security.dto.CreateUserResponse;
import org.example.msteacher.modules.teacher.domain.Teacher;
import org.example.msteacher.modules.teacher.domain.exceptions.EmailAlreadyExistsException;
import org.example.msteacher.modules.teacher.domain.exceptions.InactiveSchoolException;
import org.example.msteacher.modules.teacher.domain.exceptions.InvalidSchoolException;
import org.example.msteacher.modules.teacher.domain.exceptions.RegistrationAlreadyExistsException;
import org.example.msteacher.modules.teacher.infra.dtos.CreateTeacherRequestDto;
import org.example.msteacher.modules.teacher.infra.persistence.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateTeacherUseCase {

    private final TeacherRepository teacherRepository;
    private final SchoolClient schoolClient;
    private final SecurityClient securityClient;

    public CreateTeacherUseCase(
            TeacherRepository teacherRepository,
            SchoolClient schoolClient,
            SecurityClient securityClient) {
        this.teacherRepository = teacherRepository;
        this.schoolClient = schoolClient;
        this.securityClient = securityClient;
    }

    @Transactional
    public Teacher execute(CreateTeacherRequestDto data, String authorizationHeader) {

        if (teacherRepository.existsByRegistration(data.registration())) {
            throw new RegistrationAlreadyExistsException("A matrícula informada já está em uso.");
        }

        SchoolValidationResponse school = fetchAndValidateSchool(data.schoolId());

        CreateUserResponse userResponse = createUserViaFeign(
                authorizationHeader,
                new CreateUserRequest(data.email(), data.password())
        );

        Teacher newTeacher = new Teacher(
                userResponse.userId(),
                school.id(),
                data.fullName(),
                data.registration(),
                data.phone(),
                data.institutionalEmail()
        );

        return teacherRepository.save(newTeacher);
    }

    private SchoolValidationResponse fetchAndValidateSchool(UUID schoolId) {
        try {
            SchoolValidationResponse school = schoolClient.findById(schoolId);
            if (school == null) {
                throw new InvalidSchoolException("Escola não encontrada.");
            }
            if (!Boolean.TRUE.equals(school.isActive())) {
                throw new InactiveSchoolException("Não é possível vincular o professor a uma escola inativa.");
            }
            return school;
        } catch (FeignException.NotFound e) {
            throw new InvalidSchoolException("Escola não encontrada.");
        } catch (InvalidSchoolException | InactiveSchoolException e) {
            throw e;
        } catch (FeignException e) {
            throw new RuntimeException("Falha ao consultar o serviço acadêmico: " + e.getMessage());
        }
    }

    private CreateUserResponse createUserViaFeign(String authorizationHeader, CreateUserRequest request) {
        try {
            return securityClient.createTeacherUser(authorizationHeader, request);
        } catch (FeignException.Conflict e) {
            throw new EmailAlreadyExistsException("O e-mail informado já está em uso.");
        } catch (FeignException e) {
            throw new RuntimeException("Falha ao criar usuário no serviço de segurança: " + e.getMessage());
        }
    }
}