package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import br.com.projeto.apigerenciamentodeestoque.service.UserUseCase.GetUserByNameUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ListCategoryProductsUseCaseTest {
    @Mock
    private CategoryProductRepository categoryProductRepository;

    @InjectMocks
    private ListCategoryProductsUseCase listCategoryProductsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se a lista de categorias de produtos é retornada corretamente(um item)")
    void execute() {
        UUID uuid = UUID.randomUUID();
        CategoryProduct categoryProductMock = new CategoryProduct();
        categoryProductMock.setId(uuid);
        categoryProductMock.setName("CategoriaTeste");
        categoryProductMock.setDescription("descrição de teste");
        List<CategoryProduct> list = new ArrayList<>();
        list.add(categoryProductMock);
        when(categoryProductRepository.findProductByNameContainingIgnoreCase("CategoriaTeste")).thenReturn(list);

        List<CategoryProduct> result = listCategoryProductsUseCase.execute("CategoriaTeste");

        assertEquals(list, result);
    }

    @Test
    @DisplayName("Testa se a lista de categorias de produtos é retornada corretamente(vários itens)")
    void execute2() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        CategoryProduct categoryProductMock1 = new CategoryProduct();
        categoryProductMock1.setId(uuid1);
        categoryProductMock1.setName("CategoriaTeste1");
        categoryProductMock1.setDescription("descrição de teste 1");

        CategoryProduct categoryProductMock2 = new CategoryProduct();
        categoryProductMock2.setId(uuid2);
        categoryProductMock2.setName("CategoriaTeste2");
        categoryProductMock2.setDescription("descrição de teste 2");

        List<CategoryProduct> list = new ArrayList<>();
        list.add(categoryProductMock1);
        list.add(categoryProductMock2);

        when(categoryProductRepository.findAll()).thenReturn(list);

        List<CategoryProduct> result = listCategoryProductsUseCase.execute(null);

        assertEquals(list, result);
    }
}