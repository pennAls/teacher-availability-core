package com.example.teacheravailabilityapi.modules.interests.infra;

import com.example.teacheravailabilityapi.modules.interests.application.usecases.CreateDisciplineInterestUseCase;
import com.example.teacheravailabilityapi.modules.interests.application.usecases.DeleteDisciplineInterestUseCase;
import com.example.teacheravailabilityapi.modules.interests.application.usecases.GetAdminInterestsReportUseCase;
import com.example.teacheravailabilityapi.modules.interests.application.usecases.GetDisciplineInterestsUseCase;
import com.example.teacheravailabilityapi.modules.interests.infra.dtos.AdminTeacherInterestsReportDto;
import com.example.teacheravailabilityapi.modules.interests.infra.dtos.CreateInterestRequestDto;
import com.example.teacheravailabilityapi.modules.interests.infra.dtos.DisciplineInterestResponseDto;
import jakarta.validation.Valid;
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
            GetDisciplineInterestsUseCase getMyDisciplineInterestsUseCase, DeleteDisciplineInterestUseCase deleteDisciplineInterestUseCase, GetAdminInterestsReportUseCase getAdminInterestsReportUseCase) {
        this.createDisciplineInterestUseCase = createDisciplineInterestUseCase;
        this.getMyDisciplineInterestsUseCase = getMyDisciplineInterestsUseCase;
        this.deleteDisciplineInterestUseCase = deleteDisciplineInterestUseCase;
        this.getAdminInterestsReportUseCase = getAdminInterestsReportUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<DisciplineInterestResponseDto> create(@Valid @RequestBody CreateInterestRequestDto data) {
        var interest = createDisciplineInterestUseCase.execute(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DisciplineInterestResponseDto(interest));
    }

    @GetMapping("/me")
    public ResponseEntity<List<DisciplineInterestResponseDto>> getMe() {
        var interests = getMyDisciplineInterestsUseCase.execute();
        return ResponseEntity.ok(interests);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteDisciplineInterestUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/admin/report")
    public ResponseEntity<List<AdminTeacherInterestsReportDto>> getAdminReport() {
        return ResponseEntity.ok(getAdminInterestsReportUseCase.execute());
    }
}