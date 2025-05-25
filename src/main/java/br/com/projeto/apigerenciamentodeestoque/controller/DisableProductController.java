package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.service.DisableProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class DisableProductController {

    @Autowired
    private DisableProductService disableProductService;

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Product> desativarProduto(@PathVariable Long id) {
        return disableProductService.desativarProduto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
