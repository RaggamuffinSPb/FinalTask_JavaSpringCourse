package ru.ragga.apistore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor; // вроде не запрещено использовать ломбок?
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users") // ticketdb.public.users
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * названия колонок == названия полей класса
     * id "bigint" not null
     * username text not null
     * password text not null (в БД хранится sha256 хэш пароля без соли)
     * apiKey text null - NULL если апикей не был выдан явно
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автоинкремент idшника
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    private String role = "ROLE_USER";

    @Column(name = "api_key", unique = true, length = 255)
    private String apiKey;

}