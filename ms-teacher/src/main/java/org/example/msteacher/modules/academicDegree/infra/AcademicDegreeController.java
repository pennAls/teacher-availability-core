package org.example.msteacher.modules.academicDegree.infra;


import jakarta.validation.Valid;
import org.example.msteacher.modules.academicDegree.application_usecase.CreateAcademicDegreeUseCase;
import org.example.msteacher.modules.academicDegree.infra.dtos.AcademicDegreeRequestDto;
import org.example.msteacher.modules.academicDegree.infra.dtos.AcademicDegreeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/academic-degrees")
public class AcademicDegreeController {

    private final CreateAcademicDegreeUseCase createAcademicDegreeUseCase;

    public AcademicDegreeController(CreateAcademicDegreeUseCase createAcademicDegreeUseCase) {
        this.createAcademicDegreeUseCase = createAcademicDegreeUseCase;
    }

    @PostMapping("create")
    public ResponseEntity<AcademicDegreeResponseDto> create(@Valid @RequestBody AcademicDegreeRequestDto data, @AuthenticationPrincipal String userIdStr) {
        UUID userId = UUID.fromString(userIdStr);
        var degree = createAcademicDegreeUseCase.execute(data,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AcademicDegreeResponseDto(degree));
    }
}