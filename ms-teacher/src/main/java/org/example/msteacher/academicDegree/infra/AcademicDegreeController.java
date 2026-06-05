package org.example.msteacher.academicDegree.infra;


import jakarta.validation.Valid;
import org.example.msteacher.academicDegree.application_usecase.CreateAcademicDegreeUseCase;
import org.example.msteacher.academicDegree.infra.dtos.AcademicDegreeRequestDto;
import org.example.msteacher.academicDegree.infra.dtos.AcademicDegreeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academic-degrees")
public class AcademicDegreeController {

    private final CreateAcademicDegreeUseCase createAcademicDegreeUseCase;

    public AcademicDegreeController(CreateAcademicDegreeUseCase createAcademicDegreeUseCase) {
        this.createAcademicDegreeUseCase = createAcademicDegreeUseCase;
    }

    @PostMapping("create")
    public ResponseEntity<AcademicDegreeResponseDto> create(@Valid @RequestBody AcademicDegreeRequestDto data) {
        var degree = createAcademicDegreeUseCase.execute(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AcademicDegreeResponseDto(degree));
    }
}