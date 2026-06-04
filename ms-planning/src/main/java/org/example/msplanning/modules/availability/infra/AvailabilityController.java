package com.example.teacheravailabilityapi.modules.availability.infra;

import com.example.teacheravailabilityapi.modules.availability.application.usecases.CreateAvailabilityUseCase;
import com.example.teacheravailabilityapi.modules.availability.application.usecases.DeleteAvailabilityUseCase;
import com.example.teacheravailabilityapi.modules.availability.application.usecases.GetAdminAvailabilityReportUseCase;
import com.example.teacheravailabilityapi.modules.availability.application.usecases.GetMyAvailabilitiesUseCase;
import com.example.teacheravailabilityapi.modules.availability.infra.dtos.AdminTeacherAvailabilityReportDto;
import com.example.teacheravailabilityapi.modules.availability.infra.dtos.AvailabilityResponseDto;
import com.example.teacheravailabilityapi.modules.availability.infra.dtos.CreateAvailabilityRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final CreateAvailabilityUseCase createAvailabilityUseCase;
    private final GetMyAvailabilitiesUseCase getMyAvailabilitiesUseCase;
    private final DeleteAvailabilityUseCase deleteAvailabilityUseCase;
    private final GetAdminAvailabilityReportUseCase getAdminAvailabilityReportUseCase;

    public AvailabilityController(
            CreateAvailabilityUseCase createAvailabilityUseCase,
            GetMyAvailabilitiesUseCase getMyAvailabilitiesUseCase,
            DeleteAvailabilityUseCase deleteAvailabilityUseCase, GetAdminAvailabilityReportUseCase getAdminAvailabilityReportUseCase) {
        this.createAvailabilityUseCase = createAvailabilityUseCase;
        this.getMyAvailabilitiesUseCase = getMyAvailabilitiesUseCase;
        this.deleteAvailabilityUseCase = deleteAvailabilityUseCase;
        this.getAdminAvailabilityReportUseCase = getAdminAvailabilityReportUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<AvailabilityResponseDto> create(@Valid @RequestBody CreateAvailabilityRequestDto data) {
        var availability = createAvailabilityUseCase.execute(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AvailabilityResponseDto(availability));
    }

    @GetMapping("/me")
    public ResponseEntity<List<AvailabilityResponseDto>> getMe() {
        var list = getMyAvailabilitiesUseCase.execute();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteAvailabilityUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/admin/report")
    public ResponseEntity<List<AdminTeacherAvailabilityReportDto>> getAdminReport() {
        return ResponseEntity.ok(getAdminAvailabilityReportUseCase.execute());
    }
}