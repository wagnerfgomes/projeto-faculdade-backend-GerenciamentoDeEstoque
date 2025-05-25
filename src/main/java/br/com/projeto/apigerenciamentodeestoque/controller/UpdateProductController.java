package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.service.UpdateProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class UpdateProductController {

    private final UpdateProductService updateProductService;

    public UpdateProductController(UpdateProductService updateProductService) {
        this.updateProductService = updateProductService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> atualizar(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        return updateProductService.atualizarProduto(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

