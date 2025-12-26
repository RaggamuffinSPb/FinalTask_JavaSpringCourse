package ru.ragga.apistore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApistoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApistoreApplication.class, args);
	}
}

/**
 * реализовано первичное заполнение БД категориями, товарами и админской учёткой;
 * выполнено через /resources/data.sql, автозапуск настроен в application.yaml
 * 1. Создание товаров (admin)
 * 2. Создание категорий товаров (admin)
 * 3. Добавление заданного количества какого-либо товара (admin)
 * 4. Вывод всех товаров (user)
 * 5. Вывод всех товаров по категориям (user)
 * 6. Вывод данной категории товаров (user)
 * 7. Отгрузки товаров (admin)
 */
