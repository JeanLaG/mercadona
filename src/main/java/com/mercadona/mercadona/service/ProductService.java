package com.mercadona.mercadona.service;

import com.mercadona.mercadona.entity.Product;
import com.mercadona.mercadona.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategorie(String categorie) {
        return productRepository.findByCategorie(categorie);
    }

    public Product addPromotion(Long id, double promotion, LocalDate startDate, LocalDate endDate) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPromotion(promotion);
            product.setStartPromotionDate(startDate);
            product.setEndPromotionDate(endDate);

            // Calcul du nouveau prix avec la remise
            double newPrice = product.getPrix() - (product.getPrix() * promotion / 100);
            product.setPrix(newPrice); // Met à jour le prix avec la promotion appliquée

            return productRepository.save(product);
        }
        return null;
    }

}
