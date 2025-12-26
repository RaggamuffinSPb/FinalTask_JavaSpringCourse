package ru.ragga.apistore.dto;

import lombok.Data;

// вспомогательный DTO для вложения
@Data
public class CategorySimpleDTO {
    private Long categoryId;
    private String categoryName;
}