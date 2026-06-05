package teacher.infra.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record CreateTeacherRequestDto(
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String institutionalEmail,

        @NotBlank(message = "A senha é obrigatória.")
        String password,

        @NotBlank(message = "O nome completo é obrigatório.")
        String fullName,

        @NotBlank(message = "A matrícula é obrigatória.")
        String registration,

        @NotBlank(message = "O telefone de contato é obrigatório.")
        String phone,

        @NotNull(message = "O ID da escola é obrigatório.")
        UUID schoolId
) {}