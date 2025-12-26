package ru.ragga.apistore.entity;

import ru.ragga.apistore.entity.Item;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "items") // Таблица в БД будет 'items'
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    // наименование
    @Column(name = "item_name", nullable = false, length = 500)
    private String itemName;
    // описание товара
    @Column(name = "item_description", columnDefinition = "TEXT")
    private String itemDescription;

    // цена за единицу
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // количество на остатке магазина
    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock = 0;

    // одна категория - несколько товаров
    @ManyToOne(fetch = FetchType.LAZY) // LAZY
    @JoinColumn(name = "category_id", nullable = false) // JOIN по ключу category_id (одноимённый в обоих таблицах)
    private Category category;
}