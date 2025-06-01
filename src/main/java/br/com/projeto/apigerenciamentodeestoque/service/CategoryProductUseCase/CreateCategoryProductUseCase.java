package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateCategoryProductUseCase {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    public CategoryProduct execute(RegisterCategoryProductDto dto) {
        if (categoryProductRepository.findByName(dto.name()) != null) {
            throw new ApiException(ErrorDetails.CATEGORY_ALREADY_EXISTS);
        }
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setName(dto.name());
        categoryProduct.setDescription(dto.description());

        categoryProductRepository.save(categoryProduct);
        log.info("A catégoria {} acabou de ser criada", categoryProduct.getName());
        return categoryProduct;
    }
}
