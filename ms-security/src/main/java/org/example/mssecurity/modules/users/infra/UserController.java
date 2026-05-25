package org.example.mssecurity.modules.users.infra;

import jakarta.validation.Valid;
import org.example.mssecurity.modules.users.application.usecases.GetAllUsersUseCase;
import org.example.mssecurity.modules.users.application.usecases.GetUserByIdUseCase;
import org.example.mssecurity.modules.users.application.usecases.UpdateUserStatusUseCase;
import org.example.mssecurity.modules.users.infra.dtos.UserResponseDto;
import org.example.mssecurity.modules.users.infra.dtos.UserStatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final UpdateUserStatusUseCase toggleUserStatusUseCase;

    public UserController(GetUserByIdUseCase getUserByIdUseCase, GetAllUsersUseCase getAllUsersUseCase, UpdateUserStatusUseCase toggleUserStatusUseCase) {
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.toggleUserStatusUseCase = toggleUserStatusUseCase;
    }

    @GetMapping("/getbyId")
    public ResponseEntity<UserResponseDto> findById() {
        UserResponseDto response = getUserByIdUseCase.execute();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> response = getAllUsersUseCase.execute();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> create(@Valid @PathVariable UUID id, @RequestBody UserStatusDto data) {
        toggleUserStatusUseCase.execute(data.isActive(),id);
        return ResponseEntity.noContent().build();
    }

}