package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCategoryProductByNameUseCaseTest {

    @Mock
    CategoryProductRepository categoryProductRepository;

    @InjectMocks
    GetCategoryProductByNameUseCase getCategoryProductByNameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a busca de uma Categoria por nome")
    void execute() {

        String nomeCategoria = "Eletrônicos";
        CategoryProduct categoriaMock = new CategoryProduct();
        categoriaMock.setName(nomeCategoria);
        when(categoryProductRepository.findByName(nomeCategoria)).thenReturn(categoriaMock);

        CategoryProduct resultado = getCategoryProductByNameUseCase.execute(nomeCategoria);


        assertNotNull(resultado);
        assertEquals(nomeCategoria, resultado.getName());
    }

    @Test
    @DisplayName("Testa a busca de uma Categoria por nome onde a categoria não existe")
    void execute2() {

        String nomeCategoria = "Inexistente";
        when(categoryProductRepository.findByName(nomeCategoria)).thenReturn(null);


        ApiException exception = assertThrows(ApiException.class, () -> {
            getCategoryProductByNameUseCase.execute(nomeCategoria);
        });
        assertEquals(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND, exception.getErrorDetails());
    }
}