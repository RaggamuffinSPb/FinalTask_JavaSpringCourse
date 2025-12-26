package ru.ragga.apistore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.ragga.apistore.dto.*;
import ru.ragga.apistore.entity.User;
import ru.ragga.apistore.exception.InvalidApiKeyException;
import ru.ragga.apistore.exception.MissingApiKeyException;
import ru.ragga.apistore.repository.UserRepository;
import ru.ragga.apistore.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UserRepository userRepository;

    // создание товара (требует авторизации)
    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody CreateItemDTO dto) {
        ItemResponseDTO created = itemService.createItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // получение всех товаров
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        List<ItemResponseDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // получение всех товаров в категории
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ItemResponseDTO>> getItemsByCategory(@PathVariable Long categoryId) {
        List<ItemResponseDTO> items = itemService.getItemsByCategory(categoryId);
        return ResponseEntity.ok(items);
    }
    // изменение остатков (требует авторизации + api-key)
    @PostMapping("/update-stock")
    public ResponseEntity<ItemResponseDTO> updateItemStock(
            @Valid @RequestBody UpdateItemStockWithIdDTO dto,
            @RequestHeader(value = "X-API-Key", required = false) String apiKeyHeader,
            Authentication authentication) {

        // ищем непустой api-key в заголовке
        if (apiKeyHeader == null || apiKeyHeader.isBlank()) {
            throw new MissingApiKeyException("API key is required for this endpoint");
        }

        // получаем username из аутентификации (JWT)
        String username = authentication.getName();

        // ищем пользователя в БД 
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // и проверяем его api-key
        if (user.getApiKey() == null || !user.getApiKey().equals(apiKeyHeader)) {
            throw new InvalidApiKeyException("Invalid or missing API key");
        }

        // если всё ОК - выполняем защищённый апикеем метод
        ItemResponseDTO updated = itemService.updateItemStock(dto);
        return ResponseEntity.ok(updated);
    }
}
