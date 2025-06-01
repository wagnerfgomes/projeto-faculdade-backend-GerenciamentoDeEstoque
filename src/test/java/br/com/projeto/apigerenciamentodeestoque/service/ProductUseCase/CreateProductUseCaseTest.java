package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryProductRepository categoryProductRepository;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar produto com categoria existente")
    void execute() {
        ProductDTO dto = new ProductDTO("Produto1", "desc", new BigDecimal(100.0), 10, "Categoria1");
        CategoryProduct categoria = new CategoryProduct();
        categoria.setName("Categoria1");

        when(productRepository.findProductByName("Produto1")).thenReturn(null);
        when(categoryProductRepository.findByName("Categoria1")).thenReturn(categoria);
        when(categoryProductRepository.findByName("Sem Categoria")).thenReturn(null);

        Product produtoSalvo = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(produtoSalvo);

        Product result = createProductUseCase.execute(dto);

        assertNotNull(result);
        verify(productRepository).save(any(Product.class));
        assertEquals(categoria, result.getCategoryProduct());
    }

    @Test
    @DisplayName("Deve criar produto com categoria padrão quando categoria não existir")
    void execute2() {
        ProductDTO dto = new ProductDTO("Produto2", "desc", new BigDecimal(100.0), 10, "CategoriaInexistente");
        when(productRepository.findProductByName("Produto2")).thenReturn(null);
        when(categoryProductRepository.findByName("CategoriaInexistente")).thenReturn(null);

        CategoryProduct defaultCategory = new CategoryProduct();
        defaultCategory.setName("Sem Categoria");
        when(categoryProductRepository.findByName("Sem Categoria")).thenReturn(defaultCategory);

        Product produtoSalvo = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(produtoSalvo);

        Product result = createProductUseCase.execute(dto);

        assertNotNull(result);
        assertEquals(defaultCategory, result.getCategoryProduct());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a categoria for nula")
    void execute3() {
        ProductDTO dto = new ProductDTO("Produto3", "desc", new BigDecimal(100.0), 10, null);
        ApiException ex = assertThrows(ApiException.class, () -> createProductUseCase.execute(dto));
        assertEquals(ErrorDetails.CATEGORY_PRODUCT_REQUIRED, ex.getErrorDetails());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto já existir")
    void execute4() {
        ProductDTO dto = new ProductDTO("Produto4", "desc", new BigDecimal(100.0), 10, "Categoria1");
        when(productRepository.findProductByName("Produto4")).thenReturn(new Product());
        ApiException ex = assertThrows(ApiException.class, () -> createProductUseCase.execute(dto));
        assertEquals(ErrorDetails.PRODUCT_ALREADY_EXISTS, ex.getErrorDetails());
    }
}
