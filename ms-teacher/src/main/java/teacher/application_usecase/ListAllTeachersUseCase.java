package teacher.application_usecase;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;
import teacher.infra.dtos.TeacherResponseDto;
import teacher.infra.persistence.TeacherRepository;

@Service
public class ListAllTeachersUseCase {

    private final TeacherRepository teacherRepository;

    public ListAllTeachersUseCase(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDto> execute() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherResponseDto::new)
                .collect(Collectors.toList());
    }
}
