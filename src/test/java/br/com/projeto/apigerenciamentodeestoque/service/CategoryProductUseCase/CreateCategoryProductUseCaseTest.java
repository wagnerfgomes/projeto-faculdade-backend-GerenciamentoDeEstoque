package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterDTO;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CreateCategoryProductUseCaseTest {

    @Mock
    CategoryProductRepository categoryProductRepository;

    @InjectMocks
    CreateCategoryProductUseCase createCategoryProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a criação de uma Categoria")
    void execute() {

        RegisterCategoryProductDto dtoMock = new RegisterCategoryProductDto("CategoriaTeste", "descrição de teste");
        when(categoryProductRepository.findByName(dtoMock.name())).thenReturn(null);

        CategoryProduct returnCategory = createCategoryProductUseCase.execute(dtoMock);

        assert(returnCategory != null);
    }

    @Test
    @DisplayName("Testa a criação de uma categoria onde a categoria já existe")
    void execute2() {
        UUID uuid = UUID.randomUUID();
        CategoryProduct categoryProductMock = new CategoryProduct();
        categoryProductMock.setId(uuid);
        categoryProductMock.setName("CategoriaTeste");
        categoryProductMock.setDescription("descrição de teste");
        RegisterCategoryProductDto dtoMock = new RegisterCategoryProductDto("CategoriaTeste", "descrição de teste");
        when(categoryProductRepository.findByName(dtoMock.name())).thenReturn(categoryProductMock);

        ApiException exception = assertThrows(ApiException.class, () -> {;
            createCategoryProductUseCase.execute(dtoMock);}
        );

        assertEquals(ErrorDetails.CATEGORY_ALREADY_EXISTS, exception.getErrorDetails());
    }
}