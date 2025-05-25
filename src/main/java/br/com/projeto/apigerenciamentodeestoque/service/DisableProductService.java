package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisableProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> desativarProduto(Long id) {
        return productRepository.findById(id).map(produto -> {
            produto.setActive(false);
            return productRepository.save(produto);
        });
    }
}
