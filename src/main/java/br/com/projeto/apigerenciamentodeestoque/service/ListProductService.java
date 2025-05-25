package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listarProdutos(Optional<String> nameOpt) {
        return nameOpt
                .filter(name -> !name.isBlank())
                .map(name -> productRepository.findByNameContainingIgnoreCase(name))
                .orElseGet(productRepository::findAll);
    }
    }

