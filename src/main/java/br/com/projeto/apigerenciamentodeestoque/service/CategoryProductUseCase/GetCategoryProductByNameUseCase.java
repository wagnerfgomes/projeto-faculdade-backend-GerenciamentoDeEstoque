package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetCategoryProductByNameUseCase {

    @Autowired
    CategoryProductRepository categoryProductRepository;

    public CategoryProduct execute(String name) {
        CategoryProduct categoryProduct = categoryProductRepository.findByName(name);

        if (categoryProduct == null) {
            log.error("Categoria de produto não encontrada: {}", name);
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }
        log.info("Categoria de produto encontrada: {}", categoryProduct.getName());
        return categoryProduct;
    }
}
