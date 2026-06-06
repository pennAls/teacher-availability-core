package org.example.msteacher.clients.academic;

import org.example.msteacher.clients.academic.dto.SchoolValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-academic", path = "/schools")
public interface SchoolClient {

    @GetMapping("/{id}")
    SchoolValidationResponse findById(@PathVariable("id") UUID id);
}
