package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
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

class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryProductRepository categoryProductRepository;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void execute() {
        Product produto = new Product();
        produto.setName("Produto");
        CategoryProduct categoria = new CategoryProduct();
        categoria.setName("NovaCategoria");

        UpdateProductDTO dto = new UpdateProductDTO("NovoNome", 5, new BigDecimal(200.00), "NovaDesc", "NovaCategoria");

        when(productRepository.findProductByName("Produto")).thenReturn(produto);
        when(categoryProductRepository.findByName("NovaCategoria")).thenReturn(categoria);
        when(productRepository.save(any(Product.class))).thenReturn(produto);

        Product result = updateProductUseCase.execute("Produto", dto);

        assertEquals("NovoNome", result.getName());
        assertEquals(new BigDecimal(200.00), result.getPrice());
        assertEquals("NovaDesc", result.getDescription());
        assertEquals(5, result.getQuantity());
        assertEquals(categoria, result.getCategoryProduct());
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não encontrado")
    void execute2() {
        UpdateProductDTO dto = new UpdateProductDTO("NovoNome", 5, new BigDecimal(200.00), "NovaDesc", "NovaCategoria");
        when(productRepository.findProductByName("Inexistente")).thenReturn(null);

        ApiException ex = assertThrows(ApiException.class, () -> updateProductUseCase.execute("Inexistente", dto));
        assertEquals(ErrorDetails.PRODUCT_NOT_FOUND, ex.getErrorDetails());
    }

    @Test
    @DisplayName("Deve lançar exceção quando categoria não encontrada")
    void execute3() {
        Product produto = new Product();
        UpdateProductDTO dto = new UpdateProductDTO("NovoNome", 5, new BigDecimal(200.00), "NovaDesc", "CategoriaInexistente");

        when(productRepository.findProductByName("Produto")).thenReturn(produto);
        when(categoryProductRepository.findByName("CategoriaInexistente")).thenReturn(null);

        ApiException ex = assertThrows(ApiException.class, () -> updateProductUseCase.execute("Produto", dto));
        assertEquals(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND, ex.getErrorDetails());
    }
}
