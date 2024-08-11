package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
public class ItemDtoCreate {
    @NotNull(message = "Название не должно быть пустым")
    @NotBlank(message = "Название не должно быть пустым")
    private String name;
    @NotNull(message = "Описание не должно быть пустым")
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;
    @NotNull(message = "Поле available не может быть пустым")
    private Boolean available;
}
