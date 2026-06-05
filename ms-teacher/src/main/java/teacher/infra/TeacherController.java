package teacher.infra;

import jakarta.validation.Valid;
import org.hibernate.mapping.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teacher.application_usecase.CreateTeacherUseCase;
import teacher.application_usecase.GetTeacherProfileUseCase;
import teacher.application_usecase.ListAllTeachersUseCase;
import teacher.infra.dtos.CreateTeacherRequestDto;
import teacher.infra.dtos.TeacherResponseDto;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final CreateTeacherUseCase createTeacherUseCase;
    private final GetTeacherProfileUseCase getTeacherProfileUseCase;
    private final ListAllTeachersUseCase listAllTeachersUseCase;

    public TeacherController(
            CreateTeacherUseCase createTeacherUseCase,
            GetTeacherProfileUseCase getTeacherProfileUseCase,
            ListAllTeachersUseCase listAllTeachersUseCase) {
        this.createTeacherUseCase = createTeacherUseCase;
        this.getTeacherProfileUseCase = getTeacherProfileUseCase;
        this.listAllTeachersUseCase = listAllTeachersUseCase;
    }

    @PostMapping("create")
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody CreateTeacherRequestDto data) {
        var teacher = createTeacherUseCase.execute(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TeacherResponseDto(teacher));
    }

    @GetMapping("/me")
    public ResponseEntity<TeacherResponseDto> getMe() {
        var teacher = getTeacherProfileUseCase.execute();
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TeacherResponseDto>> getAll() {
        var teachers = listAllTeachersUseCase.execute();
        return ResponseEntity.ok(teachers);
    }
}
