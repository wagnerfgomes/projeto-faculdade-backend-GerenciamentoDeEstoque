package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.service.CategoryProductService;
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
    
    @PostMapping("/create")
    public ResponseEntity createCategoryProduct(@RequestBody RegisterCategoryProductDto registerCategoryProductDto) {
        CategoryProduct categoryProduct = categoryProductService.createCategoryProduct(registerCategoryProductDto);
        return ResponseEntity.status(201).body(categoryProduct);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryProduct>> listCategoryProducts(@RequestParam(name = "nameopt") Optional<String> nameopt) {
        List<CategoryProduct> categoryProducts = categoryProductService.listCategoryProducts(nameopt);
        return ResponseEntity.ok().body(categoryProducts);
    }

    @GetMapping
    public ResponseEntity<CategoryProduct> getCategoryByName(@RequestParam(name = "nameopt") String nameopt) {
        var categoryProducts = categoryProductService.categoryByName(nameopt);
        return ResponseEntity.ok().body(categoryProducts);
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryProduct> updateCategoryProduct(@RequestBody UpdateCategoryProductDto updateCategoryProductDto) {
        CategoryProduct updatedCategoryProduct = categoryProductService.updateCategoryProduct(updateCategoryProductDto);
        return ResponseEntity.ok().body(updatedCategoryProduct);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity deleteCategoryProduct(@PathVariable String name) {
        categoryProductService.deleteCategoryProduct(name);
        return ResponseEntity.noContent().build();
    }

}
