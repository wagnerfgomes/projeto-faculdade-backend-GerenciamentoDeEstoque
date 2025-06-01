package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListProductsUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductsUseCase listProductsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os produtos quando o nome for nulo ou vazio")
    void execute() {
        Product p1 = new Product();
        Product p2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> result1 = listProductsUseCase.execute(null);
        List<Product> result2 = listProductsUseCase.execute("");

        assertEquals(2, result1.size());
        assertEquals(2, result2.size());
    }

    @Test
    @DisplayName("Deve listar produtos por nome quando o nome for fornecido")
    void execute2() {
        Product p = new Product();
        when(productRepository.findProductByNameContainingIgnoreCase("abc")).thenReturn(Collections.singletonList(p));

        List<Product> result = listProductsUseCase.execute("abc");

        assertEquals(1, result.size());
    }
}
