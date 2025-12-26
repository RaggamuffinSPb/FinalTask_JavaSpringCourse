package ru.ragga.apistore.repository;


import org.springframework.data.jpa.repository.Query;
import ru.ragga.apistore.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // поиск товаров по категории
    List<Item> findByCategoryCategoryId(Long categoryId);

    // полный список товаров с сортировкой по наименованию
    List<Item> findAllByOrderByItemNameAsc();

    // полный список товаров с сортировкой сперва по категории, потом по наименованию - так лучше выглядит
    @Query("SELECT i FROM Item i ORDER BY i.category.categoryId ASC, i.itemId ASC")
    List<Item> findAllOrderByCategoryAndItemId();
}