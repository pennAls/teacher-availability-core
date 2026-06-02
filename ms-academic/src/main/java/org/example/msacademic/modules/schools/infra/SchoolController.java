package org.example.msacademic.modules.schools.infra;

import jakarta.validation.Valid;
import org.example.msacademic.modules.schools.application.usecase.CreateSchoolUseCase;
import org.example.msacademic.modules.schools.application.usecase.GetAllSchoolsUseCase;
import org.example.msacademic.modules.schools.application.usecase.GetSchoolByIdUseCase;
import org.example.msacademic.modules.schools.application.usecase.UpdateSchoolStatusUseCase;
import org.example.msacademic.modules.schools.domain.School;
import org.example.msacademic.modules.schools.infra.dtos.SchoolRequestDto;
import org.example.msacademic.modules.schools.infra.dtos.SchoolResponseDto;
import org.example.msacademic.modules.schools.infra.dtos.SchoolStatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    private final CreateSchoolUseCase createSchoolUseCase;
    private final GetAllSchoolsUseCase getAllSchoolsUseCase;
    private final UpdateSchoolStatusUseCase updateSchoolStatusUseCase;
    private final GetSchoolByIdUseCase getSchoolByIdUseCase;

    public SchoolController(CreateSchoolUseCase createSchoolUseCase, GetAllSchoolsUseCase getAllSchoolsUseCase, UpdateSchoolStatusUseCase updateSchoolStatusUseCase, GetSchoolByIdUseCase getSchoolByIdUseCase) {
        this.createSchoolUseCase = createSchoolUseCase;
        this.getAllSchoolsUseCase = getAllSchoolsUseCase;
        this.updateSchoolStatusUseCase = updateSchoolStatusUseCase;
        this.getSchoolByIdUseCase = getSchoolByIdUseCase;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<SchoolResponseDto>> getAllSchools() {
        var schools = getAllSchoolsUseCase.execute()
                .stream()
                .map(SchoolResponseDto::new)
                .toList();

        return ResponseEntity.ok(schools);
    }

    @PostMapping("/create")
    public ResponseEntity<SchoolResponseDto> createSchool(@RequestBody @Valid SchoolRequestDto data) {
        School newSchool = createSchoolUseCase.execute(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SchoolResponseDto(newSchool));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid SchoolStatusDto data
    ) {
        updateSchoolStatusUseCase.execute(id, data.isActive());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<SchoolResponseDto> getById(@PathVariable UUID id) {
        var school = getSchoolByIdUseCase.execute(id);
        return ResponseEntity.ok(new SchoolResponseDto(school));
    }
}
