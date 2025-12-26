package ru.ragga.apistore.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemResponseDTO {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private BigDecimal price;
    private Integer quantityInStock;
    // урезанный DTO категорий
    private CategorySimpleDTO category;
}
