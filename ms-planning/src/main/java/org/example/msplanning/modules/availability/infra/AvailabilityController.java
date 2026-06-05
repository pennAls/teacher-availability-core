package org.example.msplanning.modules.availability.infra;

import jakarta.validation.Valid;
import org.example.msplanning.modules.availability.application.usecases.CreateAvailabilityUseCase;
import org.example.msplanning.modules.availability.application.usecases.DeleteAvailabilityUseCase;
import org.example.msplanning.modules.availability.application.usecases.GetAdminAvailabilityReportUseCase;
import org.example.msplanning.modules.availability.application.usecases.GetMyAvailabilitiesUseCase;
import org.example.msplanning.modules.availability.infra.dtos.AdminTeacherAvailabilityReportDto;
import org.example.msplanning.modules.availability.infra.dtos.AvailabilityResponseDto;
import org.example.msplanning.modules.availability.infra.dtos.CreateAvailabilityRequestDto;
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
            DeleteAvailabilityUseCase deleteAvailabilityUseCase,
            GetAdminAvailabilityReportUseCase getAdminAvailabilityReportUseCase
    ) {
        this.createAvailabilityUseCase = createAvailabilityUseCase;
        this.getMyAvailabilitiesUseCase = getMyAvailabilitiesUseCase;
        this.deleteAvailabilityUseCase = deleteAvailabilityUseCase;
        this.getAdminAvailabilityReportUseCase = getAdminAvailabilityReportUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<AvailabilityResponseDto> create(
            @Valid @RequestBody CreateAvailabilityRequestDto data,
            @RequestHeader("Authorization") String bearerToken
    ) {
        var availability = createAvailabilityUseCase.execute(data, bearerToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AvailabilityResponseDto(availability));
    }

    @GetMapping("/me")
    public ResponseEntity<List<AvailabilityResponseDto>> getMe(
            @RequestHeader("Authorization") String bearerToken
    ) {
        var list = getMyAvailabilitiesUseCase.execute(bearerToken);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String bearerToken
    ) {
        deleteAvailabilityUseCase.execute(id, bearerToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/report")
    public ResponseEntity<List<AdminTeacherAvailabilityReportDto>> getAdminReport(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(
                getAdminAvailabilityReportUseCase.execute(bearerToken)
        );
    }
}