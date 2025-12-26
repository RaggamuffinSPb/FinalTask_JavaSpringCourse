package ru.ragga.apistore.service;

import ru.ragga.apistore.dto.*;
import ru.ragga.apistore.dto.CategorySimpleDTO;
import ru.ragga.apistore.entity.Item;
import ru.ragga.apistore.entity.Category;
import ru.ragga.apistore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    // создание товара
    @Transactional
    public ItemResponseDTO createItem(CreateItemDTO createItemDto) {
        // получаем сущность Category, а не DTO
        Category category = categoryService.getCategoryEntityById(createItemDto.getCategoryId());

        Item item = new Item();
        item.setItemName(createItemDto.getItemName());
        item.setItemDescription(createItemDto.getItemDescription());
        item.setPrice(createItemDto.getPrice());
        item.setQuantityInStock(createItemDto.getQuantityInStock());
        item.setCategory(category);

        Item saved = itemRepository.save(item);
        return mapToResponseDTO(saved);
    }

    // получение всех товаров
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> getAllItems() {
        return itemRepository.findAllOrderByCategoryAndItemId().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // получение товаров по категории
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> getItemsByCategory(Long categoryId) {
        // проверяем, что такая категория есть;
        // возвращаем список товаров по заведомо существующей категории
        categoryService.getCategoryEntityById(categoryId);
        return itemRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // добавление/отгрузка товара (количество на складе)
    @Transactional
    public ItemResponseDTO updateItemStock(UpdateItemStockWithIdDTO dto) {
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + dto.getItemId() + " не найден"));

        int newQuantity = item.getQuantityInStock() + dto.getQuantity();
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Недостаточно товара на складе для отгрузки. Доступно: " + item.getQuantityInStock());
        }
        item.setQuantityInStock(newQuantity);
        Item updated = itemRepository.save(item);
        return mapToResponseDTO(updated);
    }

    // маппинг Item - ItemResponseDTO
    private ItemResponseDTO mapToResponseDTO(Item item) {
        ItemResponseDTO responseDto = new ItemResponseDTO();
        responseDto.setItemId(item.getItemId());
        responseDto.setItemName(item.getItemName());
        responseDto.setItemDescription(item.getItemDescription());
        responseDto.setPrice(item.getPrice());
        responseDto.setQuantityInStock(item.getQuantityInStock());

        // маппим вложенную категорию
        CategorySimpleDTO categorySimpleDto = new CategorySimpleDTO();
        categorySimpleDto.setCategoryId(item.getCategory().getCategoryId());
        categorySimpleDto.setCategoryName(item.getCategory().getCategoryName());
        responseDto.setCategory(categorySimpleDto);

        return responseDto;
    }
}