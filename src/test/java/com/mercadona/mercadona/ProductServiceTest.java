package com.mercadona.mercadona.service;

import com.mercadona.mercadona.entity.Product;
import com.mercadona.mercadona.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepo productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductsByCategorie() {
        String categorie = "Electronics";
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findByCategorie(categorie)).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getProductsByCategorie(categorie);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByCategorie(categorie);
    }

    @Test
    void testAddPromotion_ProductExists() {
        Long productId = 1L;
        double promotion = 10.0;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(10);

        Product product = new Product();
        product.setId(productId);
        product.setPrix(100.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.addPromotion(productId, promotion, startDate, endDate);

        assertNotNull(updatedProduct);
        assertEquals(90.0, updatedProduct.getPrix(), 0.01);  // Checks the discounted price
        assertEquals(promotion, updatedProduct.getPromotion());
        assertEquals(startDate, updatedProduct.getStartPromotionDate());
        assertEquals(endDate, updatedProduct.getEndPromotionDate());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testAddPromotion_ProductNotExists() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Product result = productService.addPromotion(productId, 10.0, LocalDate.now(), LocalDate.now().plusDays(10));

        assertNull(result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(0)).save(any(Product.class));
    }
}
