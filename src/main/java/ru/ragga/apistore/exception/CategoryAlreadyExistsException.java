package ru.ragga.apistore.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        // ошибка при создании категории, которая уже есть в БД
        super(message);
    }
}
