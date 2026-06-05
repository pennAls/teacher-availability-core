package org.example.msteacher.academicDegree.application_usecase;

import org.example.mssecurity.utils.UseAuth;
import org.example.msteacher.academicDegree.domain.AcademicDegree;
import org.example.msteacher.academicDegree.infra.dtos.AcademicDegreeRequestDto;
import org.example.msteacher.academicDegree.infra.persistence.AcademicDegreeRepository;
import org.springframework.stereotype.Service;
import teacher.domain.exceptions.TeacherNotFoundException;
import teacher.infra.persistence.TeacherRepository;

@Service
public class CreateAcademicDegreeUseCase {
    private final AcademicDegreeRepository academicDegreeRepository;
    private final TeacherRepository teacherRepository;

    public CreateAcademicDegreeUseCase(AcademicDegreeRepository academicDegreeRepository, TeacherRepository teacherRepository) {
        this.academicDegreeRepository = academicDegreeRepository;
        this.teacherRepository = teacherRepository;
    }

    public AcademicDegree execute(AcademicDegreeRequestDto data) {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado para o usuário logado."));

        AcademicDegree newDegree = new AcademicDegree(
                teacher,
                data.category(),
                data.institution(),
                data.course(),
                data.year()
        );

        return academicDegreeRepository.save(newDegree);
    }
}