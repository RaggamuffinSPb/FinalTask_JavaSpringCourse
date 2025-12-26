package ru.ragga.apistore.dto;
import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private String description;
}