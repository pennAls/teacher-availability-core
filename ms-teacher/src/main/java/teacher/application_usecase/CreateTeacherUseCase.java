package teacher.application_usecase;

import jakarta.transaction.Transactional;
import org.example.mssecurity.exceptions.InactiveEntityException;
import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.modules.users.domain.exceptions.EmailAlreadyExistsException;
import org.example.mssecurity.modules.users.domain.types.UserRole;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teacher.domain.Teacher;
import teacher.domain.exceptions.RegistrationAlreadyExistsException;
import teacher.infra.dtos.CreateTeacherRequestDto;
import teacher.infra.persistence.TeacherRepository;

@Service
public class CreateTeacherUseCase {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateTeacherUseCase(
            TeacherRepository teacherRepository,
            UserRepository userRepository,
            SchoolRepository schoolRepository,
            PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Teacher execute(CreateTeacherRequestDto data) {

        if (userRepository.existsByEmail(data.email())) {
            throw new EmailAlreadyExistsException("O e-mail informado já está em uso.");
        }

        if (teacherRepository.existsByRegistration(data.registration())) {
            throw new RegistrationAlreadyExistsException("A matrícula informada já está em uso.");
        }

        var school = schoolRepository.findById(data.schoolId())
                .orElseThrow(() -> new SchoolNotFoundException("Escola não encontrada."));

        if (!school.getIsActive()) {
            throw new InactiveEntityException("Não é possível vincular o professor a uma Escola inativa.");
        }

        User newUser = new User(
                data.email(),
                passwordEncoder.encode(data.password()),
                UserRole.TEACHER
        );
        User savedUser = userRepository.save(newUser);

        Teacher newTeacher = new Teacher(
                savedUser,
                data.fullName(),
                data.registration(),
                data.phone(),
                school,
                data.institutionalEmail()
        );

        return teacherRepository.save(newTeacher);
    }
}