package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UpdateCategoryProductUseCase {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    public Optional<CategoryProduct> execute(UUID id, UpdateCategoryProductDto dto) {

        if(dto.description() == null) {
            log.error("Erro!! Campos vazios na atualização da categoria de produto");
            throw new ApiException(ErrorDetails.EMPTY_FIELDS);
        }

        Optional<CategoryProduct> categoryProduct = categoryProductRepository.findById(id);
        if (categoryProduct.isEmpty()) {
            log.error("Erro!! Categoria de produto não encontrada para o ID: {}", id);
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }
        categoryProduct.get().setDescription(dto.description());
        categoryProductRepository.save(categoryProduct.get());
        log.info("Categoria de produto atualizada com sucesso: {}", categoryProduct.get().getName());
        return categoryProduct;
    }
}
