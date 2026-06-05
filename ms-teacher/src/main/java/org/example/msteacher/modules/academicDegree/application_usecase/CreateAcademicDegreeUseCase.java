package org.example.msteacher.modules.academicDegree.application_usecase;
import org.example.msteacher.modules.academicDegree.domain.AcademicDegree;
import org.example.msteacher.modules.academicDegree.infra.dtos.AcademicDegreeRequestDto;
import org.example.msteacher.modules.academicDegree.infra.persistence.AcademicDegreeRepository;
import org.springframework.stereotype.Service;
import org.example.msteacher.modules.teacher.domain.exceptions.TeacherNotFoundException;
import org.example.msteacher.modules.teacher.infra.persistence.TeacherRepository;

import java.util.UUID;

@Service
public class CreateAcademicDegreeUseCase {
    private final AcademicDegreeRepository academicDegreeRepository;
    private final TeacherRepository teacherRepository;

    public CreateAcademicDegreeUseCase(AcademicDegreeRepository academicDegreeRepository, TeacherRepository teacherRepository) {
        this.academicDegreeRepository = academicDegreeRepository;
        this.teacherRepository = teacherRepository;
    }

    public AcademicDegree execute(AcademicDegreeRequestDto data, UUID userId) {

        var teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado para o usuário logado."));

        AcademicDegree newDegree = new AcademicDegree(
                teacher,
                data.category(),
                data.institution(),
                data.course(),
                data.year()
        );

        return academicDegreeRepository.save(newDegree);
    }
}