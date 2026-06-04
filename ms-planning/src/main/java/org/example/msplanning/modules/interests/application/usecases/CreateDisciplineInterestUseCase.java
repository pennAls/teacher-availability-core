package com.example.teacheravailabilityapi.modules.interests.application.usecases;
import com.example.teacheravailabilityapi.exceptions.InactiveEntityException;
import com.example.teacheravailabilityapi.modules.disciplines.domain.exceptions.DisciplineNotFoundException;
import com.example.teacheravailabilityapi.modules.disciplines.infra.persistence.DisciplineRepository;
import com.example.teacheravailabilityapi.modules.interests.domain.DisciplineInterest;
import com.example.teacheravailabilityapi.modules.interests.domain.exceptions.DuplicateInterestException;
import com.example.teacheravailabilityapi.modules.interests.domain.exceptions.UniqueSchoolViolationException;
import com.example.teacheravailabilityapi.modules.interests.infra.dtos.CreateInterestRequestDto;
import com.example.teacheravailabilityapi.modules.interests.infra.persistence.DisciplineInterestRepository;
import com.example.teacheravailabilityapi.modules.teacher.domain.exceptions.TeacherNotFoundException;
import com.example.teacheravailabilityapi.modules.teacher.infra.persistence.TeacherRepository;
import com.example.teacheravailabilityapi.utils.UseAuth;
import org.springframework.stereotype.Service;

@Service
public class CreateDisciplineInterestUseCase {

    private final DisciplineInterestRepository interestRepository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;

    public CreateDisciplineInterestUseCase(
            DisciplineInterestRepository interestRepository,
            TeacherRepository teacherRepository,
            DisciplineRepository disciplineRepository) {
        this.interestRepository = interestRepository;
        this.teacherRepository = teacherRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public DisciplineInterest execute(CreateInterestRequestDto data) {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        var discipline = disciplineRepository.findById(data.disciplineId())
                .orElseThrow(() -> new DisciplineNotFoundException("Disciplina não encontrada."));

        if (interestRepository.existsByTeacherIdAndDisciplineId(teacher.getId(), discipline.getId())) {
            throw new DuplicateInterestException("Você já indicou interesse nesta disciplina.");
        }
        if (!discipline.getIsActive()) {
            throw new InactiveEntityException("Não é possível registrar interesse em uma disciplina inativa.");
        }
        if (!discipline.getSchool().getId().equals(teacher.getSchool().getId())) {
            throw new UniqueSchoolViolationException(
                    "Restrição de Escola: Você só pode registrar interesse em disciplinas vinculadas à " + teacher.getSchool().getName() + "."
            );
        }

        DisciplineInterest interest = new DisciplineInterest(teacher, discipline, data.priority());

        return interestRepository.save(interest);
    }
}