package org.example.msplanning.modules.interests.application.usecases;

import org.example.msplanning.modules.interests.infra.dtos.DisciplineClientResponse;
import org.example.msplanning.modules.interests.infra.dtos.DisciplineInterestResponseDto;
import org.example.msplanning.modules.interests.infra.persistence.DisciplineInterestRepository;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.example.msplanning.modules.interests.infra.clients.AcademicClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetDisciplineInterestsUseCase {

    private final DisciplineInterestRepository interestRepository;
    private final TeacherClient teacherClient;
    private final AcademicClient academicClient;

    public GetDisciplineInterestsUseCase(
            DisciplineInterestRepository interestRepository,
            TeacherClient teacherClient,
            AcademicClient academicClient) {
        this.interestRepository = interestRepository;
        this.teacherClient = teacherClient;
        this.academicClient = academicClient;
    }

    public List<DisciplineInterestResponseDto> execute(String bearerToken) {

        var teacher = teacherClient.getMe(bearerToken);

        var interests = interestRepository.findByTeacherIdOrderByPriorityAsc(teacher.id());

        if (interests.isEmpty()) {
            return List.of();
        }

        var allDisciplinesMap = academicClient.getAllDisciplines(bearerToken).stream()
                .collect(Collectors.toMap(DisciplineClientResponse::id, d -> d));

        return interests.stream()
                .map(interest -> {
                    var disciplineData = allDisciplinesMap.get(interest.getDisciplineId());
                    return new DisciplineInterestResponseDto(interest, disciplineData);
                })
                .collect(Collectors.toList());
    }
}