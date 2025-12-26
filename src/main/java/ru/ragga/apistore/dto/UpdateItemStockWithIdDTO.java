package ru.ragga.apistore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateItemStockWithIdDTO {
    @NotNull(message = "ID товара обязательно")
    private Long itemId;

    // положительное число - добавить к остатку, отрицательное - отгрузить
    @NotNull(message = "Количество обязательно")
    private Integer quantity; 
}