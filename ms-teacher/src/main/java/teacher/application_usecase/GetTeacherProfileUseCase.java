package teacher.application_usecase;


import org.example.mssecurity.utils.UseAuth;
import org.springframework.stereotype.Service;
import teacher.domain.exceptions.TeacherNotFoundException;
import teacher.infra.dtos.TeacherResponseDto;
import teacher.infra.persistence.TeacherRepository;

@Service
public class GetTeacherProfileUseCase {

    private final TeacherRepository teacherRepository;

    public GetTeacherProfileUseCase(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherResponseDto execute() {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        return new TeacherResponseDto(teacher);
    }
}