package org.example.msplanning.modules.interests.infra.clients;

import org.example.msplanning.modules.interests.infra.dtos.DisciplineClientResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@SpringBootApplication
@FeignClient(name = "MS-ACADEMIC")
public interface AcademicClient {

    @GetMapping("/disciplines/getAll")
    List<DisciplineClientResponse> getAllDisciplines();
}