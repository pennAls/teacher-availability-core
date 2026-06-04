package org.example.msplanning.modules.interests.infra.clients;

import org.example.msplanning.modules.interests.infra.dtos.TeacherClientResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@SpringBootApplication
@FeignClient(name = "MS-TEACHER")
public interface TeacherClient {

    @GetMapping("/teachers/me")
    TeacherClientResponse getMe(@RequestHeader("Authorization") String token);

    @GetMapping("/teachers/getAll")
    List<TeacherClientResponse> getAllTeachers();

}
