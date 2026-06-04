package org.example.msplanning.modules.interests.application.usecases;
import org.example.msplanning.exceptions.InactiveEntityException;
import org.example.msplanning.modules.interests.domain.DisciplineInterest;
import org.example.msplanning.modules.interests.domain.exceptions.DisciplineNotFoundException;
import org.example.msplanning.modules.interests.domain.exceptions.DuplicateInterestException;
import org.example.msplanning.modules.interests.domain.exceptions.UniqueSchoolViolationException;
import org.example.msplanning.modules.interests.infra.clients.AcademicClient;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.example.msplanning.modules.interests.infra.dtos.CreateInterestRequestDto;
import org.example.msplanning.modules.interests.infra.dtos.DisciplineInterestResponseDto;
import org.example.msplanning.modules.interests.infra.persistence.DisciplineInterestRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateDisciplineInterestUseCase {

    private final DisciplineInterestRepository interestRepository;
    private final TeacherClient teacherClient;
    private final AcademicClient academicClient;

    public CreateDisciplineInterestUseCase(
            DisciplineInterestRepository interestRepository,
            TeacherClient teacherClient,
            AcademicClient academicClient
    ) {
        this.interestRepository = interestRepository;
        this.teacherClient = teacherClient;
        this.academicClient = academicClient;
    }

    public DisciplineInterestResponseDto execute(CreateInterestRequestDto dto, String bearerToken) {

        var teacher = teacherClient.getMe(bearerToken);

        var allDisciplines = academicClient.getAllDisciplines();

        var discipline = allDisciplines.stream()
                .filter(d -> d.id().equals(dto.disciplineId()))
                .findFirst()
                .orElseThrow(() -> new DisciplineNotFoundException("Disciplina não encontrada."));

        if (interestRepository.existsByTeacherIdAndDisciplineId(teacher.id(), discipline.id())) {
            throw new DuplicateInterestException("Você já indicou interesse nesta disciplina.");
        }

        if (!discipline.isActive()) {
            throw new InactiveEntityException("Não é possível registrar interesse em uma disciplina inativa.");
        }

        if (!discipline.schoolId().equals(teacher.schoolId())) {
            throw new UniqueSchoolViolationException(
                    "Restrição de Escola: Você só pode registrar interesse em disciplinas vinculadas à sua escola."
            );
        }

        DisciplineInterest interest = new DisciplineInterest(
                teacher.id(),
                discipline.id(),
                dto.priority()
        );

        var savedInterest = interestRepository.save(interest);
        return new DisciplineInterestResponseDto(savedInterest, discipline);
    }
}