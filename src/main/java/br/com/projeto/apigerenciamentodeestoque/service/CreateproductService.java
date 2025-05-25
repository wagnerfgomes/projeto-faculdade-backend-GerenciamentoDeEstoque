package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class  CreateproductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDTO productDTO) {

        if (productRepository.findByname(productDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Produto com esse nome já existe");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPriceCost(productDTO.getPriceCost());
        product.setPriceSale(productDTO.getPriceSale());
        product.setQuantity(productDTO.getQuantity());

        productRepository.save(product);

        return product;
    }


}
