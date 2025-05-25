package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.service.CreateproductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProductController {

    @Autowired
    private CreateproductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product createdProduct = productService.createProduct(productDTO);
            return ResponseEntity.status(201).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
