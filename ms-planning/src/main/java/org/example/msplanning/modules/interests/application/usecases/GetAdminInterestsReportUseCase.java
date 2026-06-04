package org.example.msplanning.modules.interests.application.usecases;

import org.example.msplanning.modules.interests.domain.DisciplineInterest;
import org.example.msplanning.modules.interests.infra.dtos.AdminTeacherInterestsReportDto;
import org.example.msplanning.modules.interests.infra.dtos.DisciplineClientResponse;
import org.example.msplanning.modules.interests.infra.dtos.TeacherClientResponse;
import org.example.msplanning.modules.interests.infra.persistence.DisciplineInterestRepository;
import org.example.msplanning.modules.interests.infra.clients.AcademicClient;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetAdminInterestsReportUseCase {

    private final DisciplineInterestRepository repository;
    private final TeacherClient teacherClient;
    private final AcademicClient academicClient;

    public GetAdminInterestsReportUseCase(
            DisciplineInterestRepository repository,
            TeacherClient teacherClient,
            AcademicClient academicClient) {
        this.repository = repository;
        this.teacherClient = teacherClient;
        this.academicClient = academicClient;
    }

    public List<AdminTeacherInterestsReportDto> execute() {

        List<DisciplineInterest> allInterests = repository.findAll();

        if (allInterests.isEmpty()) {
            return List.of();
        }


        Map<UUID, DisciplineClientResponse> discMap = academicClient.getAllDisciplines()
                .stream()
                .collect(Collectors.toMap(DisciplineClientResponse::id, d -> d));

        Map<UUID, TeacherClientResponse> teacherMap = teacherClient.getAllTeachers()
                .stream()
                .collect(Collectors.toMap(TeacherClientResponse::id, t -> t));


        var groupedByTeacherId = allInterests.stream()
                .collect(Collectors.groupingBy(DisciplineInterest::getTeacherId));

        return groupedByTeacherId.entrySet().stream().map(entry -> {
            UUID teacherId = entry.getKey();
            List<DisciplineInterest> interests = entry.getValue();

            TeacherClientResponse teacher = teacherMap.get(teacherId);
            String teacherName = (teacher != null) ? teacher.name() : "Professor Desconhecido";

            var interestsList = interests.stream().map(i -> {

                DisciplineClientResponse disc = discMap.get(i.getDisciplineId());

                String acronym = (disc != null && disc.acronym() != null) ? disc.acronym() : "N/A";
                String description = (disc != null && disc.description() != null) ? disc.description() : "Disciplina indisponível";

                return new AdminTeacherInterestsReportDto.InterestDetailDto(
                        acronym,
                        description,
                        i.getPriority()
                );
            }).toList();

            return new AdminTeacherInterestsReportDto(
                    teacherId,
                    teacherName,
                    interestsList
            );
        }).toList();
    }
}