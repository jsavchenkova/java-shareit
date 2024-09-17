package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Builder
public class UserDtoUpdate {
    private String name;

    @Email(message = "Электронная почта должна содержать символ @")
    private String email;
}
