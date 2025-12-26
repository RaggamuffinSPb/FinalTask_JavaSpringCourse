package ru.ragga.apistore.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateItemDTO {
    @NotBlank(message = "Названия товара не может быть пустым")
    private String itemName;

    private String itemDescription;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
    private BigDecimal price;

    @NotNull(message = "Количество обязательно")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer quantityInStock;

    // Клиент присылает IDшник категории
    @NotNull(message = "ID категории обязателен")
    private Long categoryId; 
}