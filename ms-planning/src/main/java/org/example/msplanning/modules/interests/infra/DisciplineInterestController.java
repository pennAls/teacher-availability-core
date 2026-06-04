package org.example.msplanning.modules.interests.infra;

import jakarta.validation.Valid;
import org.example.msplanning.modules.interests.application.usecases.CreateDisciplineInterestUseCase;
import org.example.msplanning.modules.interests.application.usecases.DeleteDisciplineInterestUseCase;
import org.example.msplanning.modules.interests.application.usecases.GetAdminInterestsReportUseCase;
import org.example.msplanning.modules.interests.application.usecases.GetDisciplineInterestsUseCase;
import org.example.msplanning.modules.interests.infra.dtos.AdminTeacherInterestsReportDto;
import org.example.msplanning.modules.interests.infra.dtos.CreateInterestRequestDto;
import org.example.msplanning.modules.interests.infra.dtos.DisciplineInterestResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/interests")
public class DisciplineInterestController {

    private final CreateDisciplineInterestUseCase createDisciplineInterestUseCase;
    private final GetDisciplineInterestsUseCase getMyDisciplineInterestsUseCase;
    private final DeleteDisciplineInterestUseCase deleteDisciplineInterestUseCase;
    private final GetAdminInterestsReportUseCase getAdminInterestsReportUseCase;

    public DisciplineInterestController(
            CreateDisciplineInterestUseCase createDisciplineInterestUseCase,
            GetDisciplineInterestsUseCase getMyDisciplineInterestsUseCase,
            DeleteDisciplineInterestUseCase deleteDisciplineInterestUseCase,
            GetAdminInterestsReportUseCase getAdminInterestsReportUseCase) {
        this.createDisciplineInterestUseCase = createDisciplineInterestUseCase;
        this.getMyDisciplineInterestsUseCase = getMyDisciplineInterestsUseCase;
        this.deleteDisciplineInterestUseCase = deleteDisciplineInterestUseCase;
        this.getAdminInterestsReportUseCase = getAdminInterestsReportUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<DisciplineInterestResponseDto> create(
            @Valid @RequestBody CreateInterestRequestDto dto,
            @RequestHeader("Authorization") String bearerToken) {
        var interest = createDisciplineInterestUseCase.execute(dto, bearerToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(interest);
    }

    @GetMapping("/me")
    public ResponseEntity<List<DisciplineInterestResponseDto>> getMe(
            @RequestHeader("Authorization") String bearerToken) {
        var interests = getMyDisciplineInterestsUseCase.execute(bearerToken);
        return ResponseEntity.ok(interests);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String bearerToken) {
        deleteDisciplineInterestUseCase.execute(id, bearerToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/report")
    public ResponseEntity<List<AdminTeacherInterestsReportDto>> getAdminReport() {
        return ResponseEntity.ok(getAdminInterestsReportUseCase.execute());
    }
}