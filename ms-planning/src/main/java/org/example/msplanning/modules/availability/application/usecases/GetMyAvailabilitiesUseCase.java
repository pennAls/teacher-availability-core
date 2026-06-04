package com.example.teacheravailabilityapi.modules.availability.application.usecases;
import com.example.teacheravailabilityapi.modules.availability.infra.dtos.AvailabilityResponseDto;
import com.example.teacheravailabilityapi.modules.availability.infra.persistence.AvailabilityRepository;
import com.example.teacheravailabilityapi.modules.teacher.domain.exceptions.TeacherNotFoundException;
import com.example.teacheravailabilityapi.modules.teacher.infra.persistence.TeacherRepository;
import com.example.teacheravailabilityapi.utils.UseAuth;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetMyAvailabilitiesUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final TeacherRepository teacherRepository;

    public GetMyAvailabilitiesUseCase(AvailabilityRepository availabilityRepository, TeacherRepository teacherRepository) {
        this.availabilityRepository = availabilityRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<AvailabilityResponseDto> execute() {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        var availabilities = availabilityRepository.findByTeacherId(teacher.getId());
        return availabilities.stream()
                .sorted((a1, a2) -> {
                    int dayCompare = a1.getDayOfWeek().compareTo(a2.getDayOfWeek());
                    if (dayCompare != 0) {
                        return dayCompare;
                    }
                    return a1.getStartTime().compareTo(a2.getStartTime());
                })
                .map(AvailabilityResponseDto::new)
                .collect(Collectors.toList());
    }
}