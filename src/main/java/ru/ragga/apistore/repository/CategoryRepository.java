package ru.ragga.apistore.repository;


import ru.ragga.apistore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // поиск категории по имени, связь с товарами по ключам
    Optional<Category> findByCategoryName(String categoryName);
}