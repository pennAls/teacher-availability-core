package com.example.teacheravailabilityapi.modules.interests.application.usecases;
import com.example.teacheravailabilityapi.modules.interests.domain.DisciplineInterest;
import com.example.teacheravailabilityapi.modules.interests.infra.dtos.AdminTeacherInterestsReportDto;
import com.example.teacheravailabilityapi.modules.interests.infra.persistence.DisciplineInterestRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAdminInterestsReportUseCase {
    private final DisciplineInterestRepository repository;

    public GetAdminInterestsReportUseCase(DisciplineInterestRepository repository) {
        this.repository = repository;
    }

    public List<AdminTeacherInterestsReportDto> execute() {
        var allInterests = repository.findAllForAdminReport();

        var groupedByTeacher = allInterests.stream()
                .collect(Collectors.groupingBy(
                        DisciplineInterest::getTeacher,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return groupedByTeacher.entrySet().stream().map(entry -> {
            var teacher = entry.getKey();
            var interestsList = entry.getValue().stream()
                    .map(i -> new AdminTeacherInterestsReportDto.InterestDetailDto(
                            i.getDiscipline().getAcronym(),
                            i.getDiscipline().getDescription(),
                            i.getPriority()
                    ))
                    .toList();

            return new AdminTeacherInterestsReportDto(
                    teacher.getId(),
                    teacher.getFullName(),
                    interestsList
            );
        }).toList();
    }
}