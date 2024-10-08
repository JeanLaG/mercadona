package com.mercadona.mercadona.controller;

import com.mercadona.mercadona.dto.PromotionDto;
import com.mercadona.mercadona.entity.Product;
import com.mercadona.mercadona.service.FileService;
import com.mercadona.mercadona.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @PostMapping("/admin/api/products/create")
    public ResponseEntity<Product> createProduct(@RequestParam("libelle") String libelle,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("prix") double prix,
                                                 @RequestParam("categorie") String categorie,
                                                 @RequestParam("image") MultipartFile imageFile) {
        // Sauvegarder l'image et obtenir l'URL
        String imageUrl = fileService.saveImage(imageFile);

        Product product = new Product();
        product.setLibelle(libelle);
        product.setDescription(description);
        product.setPrix(prix);
        product.setCategorie(categorie);
        product.setImageUrl(imageUrl);

        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("/tmp/images", filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read the file!", e);
        }
    }

    @PutMapping("/auth/api/products/promotion/{id}")
    public ResponseEntity<Product> addPromotion(
            @PathVariable Long id,
            @RequestBody PromotionDto promotionDto) {
        Product updatedProduct = productService.addPromotion(
                id,
                promotionDto.getPromotion(),
                promotionDto.getStartDate(),
                promotionDto.getEndDate()
        );
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/auth/api/products/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/auth/api/products/categorie/{categorie}")
    public ResponseEntity<List<Product>> getProductsByCategorie(@PathVariable String categorie) {
        List<Product> products = productService.getProductsByCategorie(categorie);
        return ResponseEntity.ok(products);
    }
}