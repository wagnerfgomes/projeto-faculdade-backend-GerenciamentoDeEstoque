package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListCategoryProductsUseCase {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    public List<CategoryProduct> execute(String name) {
        if (name == null || name.isEmpty()) {
            return categoryProductRepository.findAll();
        }

        List<CategoryProduct> categoryProductList =  categoryProductRepository.findProductByNameContainingIgnoreCase(name);

        return categoryProductList;
    }
}
