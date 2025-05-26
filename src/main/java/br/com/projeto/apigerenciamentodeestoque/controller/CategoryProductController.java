package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.service.CategoryProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoryproduct")
public class CategoryProductController {

    @Autowired
    CategoryProductService categoryProductService;

    @Operation(summary = "Cadastrar nova categoria de produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Categoria já existe")
    })
    @PostMapping("/create")
    public ResponseEntity createCategoryProduct(@RequestBody RegisterCategoryProductDto registerCategoryProductDto) {
        CategoryProduct categoryProduct = categoryProductService.createCategoryProduct(registerCategoryProductDto);
        return ResponseEntity.status(201).body(categoryProduct);
    }

    @Operation(summary = "Listar categorias de produto", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    })
    @GetMapping("/list")
    public ResponseEntity<List<CategoryProduct>> listCategoryProducts(@RequestParam(name = "name", required = false) String name) {
        List<CategoryProduct> categoryProducts = categoryProductService.listCategoryProducts(name);
        return ResponseEntity.ok().body(categoryProducts);
    }

    @Operation(summary = "Buscar categoria por nome", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping
    public ResponseEntity<CategoryProduct> getCategoryByName(@RequestParam(name = "name") String name) {
        var categoryProducts = categoryProductService.categoryByName(name);
        return ResponseEntity.ok().body(categoryProducts);
    }

    @Operation(summary = "Atualizar categoria de produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campos obrigatórios vazios"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/update")
    public ResponseEntity<CategoryProduct> updateCategoryProduct(@RequestParam(name="name") String name, @RequestBody UpdateCategoryProductDto updateCategoryProductDto) {
        CategoryProduct updatedCategoryProduct = categoryProductService.updateCategoryProduct(name, updateCategoryProductDto);
        return ResponseEntity.ok().body(updatedCategoryProduct);
    }

    @Operation(summary = "Deletar categoria de produto", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/delete/{name}")
    public ResponseEntity deleteCategoryProduct(@PathVariable String name) {
        categoryProductService.deleteCategoryProduct(name);
        return ResponseEntity.noContent().build();
    }

}
