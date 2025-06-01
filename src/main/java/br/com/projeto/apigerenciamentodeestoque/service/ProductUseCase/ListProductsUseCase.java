package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListProductsUseCase {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> execute(String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.findProductByNameContainingIgnoreCase(name);
    }
}
