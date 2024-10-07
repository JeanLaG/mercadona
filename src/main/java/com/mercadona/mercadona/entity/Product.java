package com.mercadona.mercadona.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "produit")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String description;
    private double prix;
    private String imageUrl;
    private String categorie;

    @Column(nullable = true)
    private double promotion;

    @Column(nullable = true)
    private LocalDate startPromotionDate;

    @Column(nullable = true)
    private LocalDate endPromotionDate;

    // Getters and setters
}
