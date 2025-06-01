package br.com.projeto.apigerenciamentodeestoque.service.CategoryProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class DeleteCategoryProductUseCase {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @Autowired
    private ProductRepository productRepository;

    public void execute(String name) {

        CategoryProduct categoryProduct = categoryProductRepository.findByName(name);
        if (categoryProduct == null) {
            log.error("Categoria de produto '{}' não encontrada", name);
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }
        CategoryProduct noCategoryProduct = categoryProductRepository.findByName("Sem Categoria");

        List<Product> products = productRepository.findByCategoryProduct(categoryProduct);

        products.forEach(product -> product.setCategoryProduct(noCategoryProduct));
        log.info("Os produtos {} foram atualizados para a categoria 'Sem Categoria'", products);

        categoryProductRepository.delete(categoryProduct);
        log.info("Categoria de produto '{}' deletada com sucesso", categoryProduct.getName());
    }
}
