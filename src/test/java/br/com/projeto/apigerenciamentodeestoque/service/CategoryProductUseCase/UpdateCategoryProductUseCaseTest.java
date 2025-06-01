package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateCategoryProductDto;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCategoryProductUseCaseTest {

    @Mock
    private CategoryProductRepository categoryProductRepository;

    @InjectMocks
    private UpdateCategoryProductUseCase updateCategoryProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve atualizar a descrição da categoria com sucesso")
    void execute() {
        UUID id = UUID.randomUUID();
        UpdateCategoryProductDto dto = new UpdateCategoryProductDto("Nova descrição");
        CategoryProduct categoryProductMock = new CategoryProduct();
        categoryProductMock.setId(id);
        categoryProductMock.setDescription("Descrição antiga");
        when(categoryProductRepository.findById(id)).thenReturn(Optional.of(categoryProductMock));
        when(categoryProductRepository.save(categoryProductMock)).thenReturn(categoryProductMock);

        Optional<CategoryProduct> result = updateCategoryProductUseCase.execute(id, dto);

        assertTrue(result.isPresent());
        assertEquals("Nova descrição", result.get().getDescription());
        verify(categoryProductRepository, times(1)).findById(id);
        verify(categoryProductRepository, times(1)).save(categoryProductMock);
    }


    @Test
    @DisplayName("Deve lançar exceção quando a descrição estiver vazia")
    void execute2() {
        UUID id = UUID.randomUUID();
        UpdateCategoryProductDto dto = new UpdateCategoryProductDto(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            updateCategoryProductUseCase.execute(id, dto);
        });

        assertEquals(ErrorDetails.EMPTY_FIELDS, exception.getErrorDetails());
        verify(categoryProductRepository, never()).findById(any());
        verify(categoryProductRepository, never()).save(any());
    }


    @Test
    @DisplayName("Deve lançar exceção quando a categoria não for encontrada")
    void execute3() {

        UUID id = UUID.randomUUID();
        UpdateCategoryProductDto dto = new UpdateCategoryProductDto("Nova descrição");

        when(categoryProductRepository.findById(id)).thenReturn(Optional.empty());


        ApiException exception = assertThrows(ApiException.class, () -> {
            updateCategoryProductUseCase.execute(id, dto);
        });

        assertEquals(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND, exception.getErrorDetails());
        verify(categoryProductRepository, times(1)).findById(id);
        verify(categoryProductRepository, never()).save(any());
    }
}