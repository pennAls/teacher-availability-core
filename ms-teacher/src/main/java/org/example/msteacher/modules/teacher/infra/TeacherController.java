package org.example.msteacher.modules.teacher.infra;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.msteacher.modules.teacher.application_usecase.CreateTeacherUseCase;
import org.example.msteacher.modules.teacher.application_usecase.GetTeacherProfileUseCase;
import org.example.msteacher.modules.teacher.application_usecase.ListAllTeachersUseCase;
import org.example.msteacher.modules.teacher.infra.dtos.CreateTeacherRequestDto;
import org.example.msteacher.modules.teacher.infra.dtos.TeacherResponseDto;

import java.util.List;

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

    @PostMapping("/create")
    public ResponseEntity<TeacherResponseDto> create(
            @Valid @RequestBody CreateTeacherRequestDto data,
            @RequestHeader("Authorization") String authorizationHeader) {
        var teacher = createTeacherUseCase.execute(data, authorizationHeader);
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
