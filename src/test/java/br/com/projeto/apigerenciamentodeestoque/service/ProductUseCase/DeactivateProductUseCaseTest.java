package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeactivateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DesactivateProductUseCase deactivateProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve desativar produto com sucesso")
    void execute() {
        Product produto = new Product();
        produto.setActive(true);

        when(productRepository.findProductByName("Produto")).thenReturn(produto);
        when(productRepository.save(any(Product.class))).thenReturn(produto);

        Product result = deactivateProductUseCase.execute("Produto");

        assertFalse(result.getActive());
        verify(productRepository).save(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não encontrado")
    void execute2() {
        when(productRepository.findProductByName("Inexistente")).thenReturn(null);

        ApiException ex = assertThrows(ApiException.class, () -> deactivateProductUseCase.execute("Inexistente"));
        assertEquals(ErrorDetails.PRODUCT_NOT_FOUND, ex.getErrorDetails());
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto já desativado")
    void execute3() {
        Product produto = new Product();
        produto.setActive(false);

        when(productRepository.findProductByName("Produto")).thenReturn(produto);

        ApiException ex = assertThrows(ApiException.class, () -> deactivateProductUseCase.execute("Produto"));
        assertEquals(ErrorDetails.PRODUCT_ALREADY_DEACTIVATED, ex.getErrorDetails());
    }
}
