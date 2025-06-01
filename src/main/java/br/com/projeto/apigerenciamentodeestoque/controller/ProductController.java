package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase.CreateProductUseCase;
import br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase.DesactivateProductUseCase;
import br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase.ListProductsUseCase;
import br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase.UpdateProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Adiciona imports do Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/produto")
public class ProductController {

    @Autowired
    CreateProductUseCase createProduct;

    @Autowired
    ListProductsUseCase listProductsUseCase;

    @Autowired
    UpdateProductUseCase updateProductUseCase;

    @Autowired
    DesactivateProductUseCase desactivateProductUseCase;

    @Operation(summary = "Cadastro de produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Categoria do produto obrigatória"),
            @ApiResponse(responseCode = "409", description = "Produto já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = createProduct.execute(productDTO);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @Operation(summary = "Listar produtos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    })
    @GetMapping("/listar")
    public ResponseEntity<?> listarProdutos(@RequestParam(required = false, name = "name") String name) {
        List<Product> produtos = listProductsUseCase.execute(name);
        return ResponseEntity.ok().body(produtos);
    }

    @Operation(summary = "Atualizar produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/update")
    public ResponseEntity<Product> atualizar(@RequestParam(name = "name") String name, @RequestBody UpdateProductDTO dto) {
        Product product = updateProductUseCase.execute(name,dto);
        return ResponseEntity.ok().body(product);
    }

    @Operation(summary = "Desativar produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto já está desativado")
    })
    @PutMapping("/desativar")
    public ResponseEntity<Product> desativarProduto(@RequestParam String name) {
        Product produto = desactivateProductUseCase.execute(name);
        return ResponseEntity.ok(produto);
    }
}
