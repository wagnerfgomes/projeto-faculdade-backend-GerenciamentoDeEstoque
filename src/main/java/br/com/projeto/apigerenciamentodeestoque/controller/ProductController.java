package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarProdutos(@RequestParam(required = false, name = "name") String name) {
        List<Product> produtos = productService.listarProdutos(name);
        return ResponseEntity.ok().body(produtos);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> atualizar(@RequestParam(name = "name") String name, @RequestBody UpdateProductDTO dto) {
        Product product = productService.atualizarProduto(name,dto);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/desativar")
    public ResponseEntity<Product> desativarProduto(@RequestParam String name) {
        Product produto = productService.desativarProduto(name);
        return ResponseEntity.ok(produto);
    }
}

