package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Builder
public class UserDtoCreate {
    private String name;

    @Email(message = "Электронная почта должна содержать символ @")
    @NotNull(message = "Электронная почта не должна быть пустой")
    @NotBlank(message = "Электронная почта не должна быть пустой")
    private String email;
}
