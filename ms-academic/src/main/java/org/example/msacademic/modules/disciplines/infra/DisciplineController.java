package org.example.msacademic.modules.disciplines.infra;


import jakarta.validation.Valid;
import org.example.msacademic.modules.disciplines.application.usecases.CreateDisciplineUseCase;
import org.example.msacademic.modules.disciplines.application.usecases.GetAllDisciplinesUseCase;
import org.example.msacademic.modules.disciplines.application.usecases.GetDisciplinesBySchoolUseCase;
import org.example.msacademic.modules.disciplines.application.usecases.UpdateDisciplineStatusUseCase;
import org.example.msacademic.modules.disciplines.infra.dtos.DisciplineRequestDto;
import org.example.msacademic.modules.disciplines.infra.dtos.DisciplineResponseDto;
import org.example.msacademic.modules.disciplines.infra.dtos.DisciplineStatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {
    private final CreateDisciplineUseCase createDisciplineUseCase;
    private final UpdateDisciplineStatusUseCase updateDisciplineStatusUseCase;
    private final GetAllDisciplinesUseCase getAllDisciplinesUseCase;
    private final GetDisciplinesBySchoolUseCase getDisciplinesBySchoolUseCase;

    public DisciplineController(
            CreateDisciplineUseCase createDisciplineUseCase,
            UpdateDisciplineStatusUseCase updateDisciplineStatusUseCase,
            GetAllDisciplinesUseCase getAllDisciplinesUseCase,
            GetDisciplinesBySchoolUseCase getDisciplinesBySchoolUseCase
    ) {
        this.createDisciplineUseCase = createDisciplineUseCase;
        this.updateDisciplineStatusUseCase = updateDisciplineStatusUseCase;
        this.getAllDisciplinesUseCase = getAllDisciplinesUseCase;
        this.getDisciplinesBySchoolUseCase = getDisciplinesBySchoolUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<DisciplineResponseDto> createDiscipline(@RequestBody @Valid DisciplineRequestDto data) {
        var newDiscipline = createDisciplineUseCase.execute(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DisciplineResponseDto(newDiscipline));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid DisciplineStatusDto data
    ) {
        updateDisciplineStatusUseCase.execute(id, data.isActive());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DisciplineResponseDto>> getAll() {
        var disciplines = getAllDisciplinesUseCase.execute()
                .stream()
                .map(DisciplineResponseDto::new)
                .toList();
        return ResponseEntity.ok(disciplines);
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<DisciplineResponseDto>> getBySchool(@PathVariable UUID schoolId) {
        var disciplines = getDisciplinesBySchoolUseCase.execute(schoolId)
                .stream()
                .map(DisciplineResponseDto::new)
                .toList();
        return ResponseEntity.ok(disciplines);
    }
}
