package br.com.projeto.apigerenciamentodeestoque.service;


import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;

import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UpdateProductService {

    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> atualizarProduto(Long id, UpdateProductDTO dto) {
        Optional<Product> produtoOptional = productRepository.findById(id);

        return produtoOptional.map(produto -> {
            if (dto.getName() != null) produto.setName(dto.getName());
            if (dto.getQuantity() != null) produto.setQuantity(dto.getQuantity());
            Optional.ofNullable(dto.getPrice())
                    .ifPresent(produto::setPrice);

            return productRepository.save(produto);
        });
    }
}

