package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDTO productDTO) {

        if (productRepository.findByname(productDTO.getName()).isPresent()) {
            throw new ApiException(ErrorDetails.PRODUCT_ALREADY_EXISTS);
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());

        productRepository.save(product);

        return product;
    }

    public List<Product> listarProdutos(Optional<String> nameOpt) {
        return nameOpt
                .filter(name -> !name.isBlank())
                .map(name -> productRepository.findByNameContainingIgnoreCase(name))
                .orElseGet(productRepository::findAll);
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

    public Optional<Product> desativarProduto(Long id) {
        return productRepository.findById(id).map(produto -> {
            produto.setActive(false);
            return productRepository.save(produto);
        });
    }
}
