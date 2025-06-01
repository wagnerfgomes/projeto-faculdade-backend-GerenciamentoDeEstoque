package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteCategoryProductUseCaseTest {

    @Mock
    private CategoryProductRepository categoryProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Logger log;

    @InjectMocks
    private DeleteCategoryProductUseCase deleteCategoryProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve deletar categoria de produto e atualizar produtos associados")
    void execute() {
        String categoria = "Bebidas";
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setName(categoria);

        CategoryProduct noCategory = new CategoryProduct();
        noCategory.setName("Sem Categoria");

        Product p1 = new Product();
        p1.setCategoryProduct(categoryProduct);
        Product p2 = new Product();
        p2.setCategoryProduct(categoryProduct);

        List<Product> products = Arrays.asList(p1, p2);

        when(categoryProductRepository.findByName(categoria)).thenReturn(categoryProduct);
        when(categoryProductRepository.findByName("Sem Categoria")).thenReturn(noCategory);
        when(productRepository.findByCategoryProduct(categoryProduct)).thenReturn(products);

        deleteCategoryProductUseCase.execute(categoria);

        verify(productRepository).findByCategoryProduct(categoryProduct);
        assertEquals(noCategory, p1.getCategoryProduct());
        assertEquals(noCategory, p2.getCategoryProduct());
        verify(categoryProductRepository).delete(categoryProduct);
    }

    @Test
    @DisplayName("Deve lançar exceção quando categoria de produto não existir")
    void execute2() {
        String categoria = "Inexistente";
        when(categoryProductRepository.findByName(categoria)).thenReturn(null);

        ApiException ex = assertThrows(ApiException.class, () -> {
            deleteCategoryProductUseCase.execute(categoria);
        });

        assertEquals(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND, ex.getErrorDetails());
        verify(categoryProductRepository, never()).delete(any());
    }


    @Test
    @DisplayName("Deve deletar categoria de produto sem produtos associados")
    void execute3() {
        String categoria = "Doces";
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setName(categoria);

        CategoryProduct noCategory = new CategoryProduct();
        noCategory.setName("Sem Categoria");

        when(categoryProductRepository.findByName(categoria)).thenReturn(categoryProduct);
        when(categoryProductRepository.findByName("Sem Categoria")).thenReturn(noCategory);
        when(productRepository.findByCategoryProduct(categoryProduct)).thenReturn(Collections.emptyList());

        deleteCategoryProductUseCase.execute(categoria);

        verify(productRepository).findByCategoryProduct(categoryProduct);
        verify(categoryProductRepository).delete(categoryProduct);
    }
}