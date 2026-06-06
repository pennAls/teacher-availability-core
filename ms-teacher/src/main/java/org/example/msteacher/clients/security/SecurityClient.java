package org.example.msteacher.clients.security;

import org.example.msteacher.clients.security.dto.CreateUserRequest;
import org.example.msteacher.clients.security.dto.CreateUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-security", path = "/users")
public interface SecurityClient {

    @PostMapping
    CreateUserResponse createTeacherUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CreateUserRequest request
    );
}
