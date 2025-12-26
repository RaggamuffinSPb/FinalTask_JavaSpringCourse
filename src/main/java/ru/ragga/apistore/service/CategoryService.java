package ru.ragga.apistore.service;

import ru.ragga.apistore.dto.CreateCategoryDTO;
import ru.ragga.apistore.dto.CategoryResponseDTO;
import ru.ragga.apistore.entity.Category;
import ru.ragga.apistore.exception.CategoryAlreadyExistsException;
import ru.ragga.apistore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDTO createCategory(CreateCategoryDTO dto) {
        // Проверяем, нет ли уже категории с таким именем
        if (categoryRepository.findByCategoryName(dto.getCategoryName()).isPresent()) {
            // в респонсе вернётся явное сообщение об ошибке
            throw new CategoryAlreadyExistsException("Категория с названием '" + dto.getCategoryName() + "' уже существует");
        }

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        Category saved = categoryRepository.save(category);
        return mapToResponseDTO(saved);
    }

    // получение всех категорий
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // получение категории по ID
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + id + " не найдена"));
    }
    @Transactional(readOnly = true)
    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + id + " не найдена"));
    }

    // связка сущности Category - CategoryResponseDTO, вспомогательный метод
    private CategoryResponseDTO mapToResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}