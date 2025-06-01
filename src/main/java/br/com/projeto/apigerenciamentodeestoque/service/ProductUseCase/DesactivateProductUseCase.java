package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesactivateProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    public Product execute(String name) {
        Product product = productRepository.findProductByName(name);
        if (product == null) {
            throw new ApiException(ErrorDetails.PRODUCT_NOT_FOUND);
        }
        if (Boolean.FALSE.equals(product.getActive())) {
            throw new ApiException(ErrorDetails.PRODUCT_ALREADY_DEACTIVATED);
        }
        product.setActive(false);
        return productRepository.save(product);
    }
}
