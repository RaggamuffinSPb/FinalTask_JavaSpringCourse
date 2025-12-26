package ru.ragga.apistore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// создание категорий товаров
public class CreateCategoryDTO {
    @NotBlank(message = "Название категории не может быть пустым")
    private String categoryName;

    private String description;
}