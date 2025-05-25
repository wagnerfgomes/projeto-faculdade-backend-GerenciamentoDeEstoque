package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.service.ListProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ListProductController {

    @Autowired
    private ListProductService listProductService;


    @GetMapping("/listar")
    public ResponseEntity<?> listarProdutos(@RequestParam(required = false) Optional<String> name) {
        try {
            List<Product> produtos = listProductService.listarProdutos(name);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao listar produtos: " + e.getMessage());
        }
    }
}
