package org.example.msplanning.modules.interests.application.usecases;

import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.example.msplanning.modules.interests.infra.persistence.DisciplineInterestRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteDisciplineInterestUseCase {

    private final DisciplineInterestRepository interestRepository;
    private final TeacherClient teacherClient;

    public DeleteDisciplineInterestUseCase(DisciplineInterestRepository interestRepository, TeacherClient teacherClient) {
        this.interestRepository = interestRepository;
        this.teacherClient = teacherClient;
    }

    public void execute(UUID interestId, String bearerToken) {

        var teacher = teacherClient.getMe(bearerToken);

        var interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("Registro de interesse não encontrado."));

        if (!interest.getTeacherId().equals(teacher.id())) {
            throw new RuntimeException("Acesso negado: Você não pode remover o interesse de outro professor.");
        }

        interestRepository.delete(interest);
    }
}