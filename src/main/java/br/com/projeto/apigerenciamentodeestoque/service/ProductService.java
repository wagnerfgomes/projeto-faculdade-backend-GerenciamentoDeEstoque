package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
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

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    public Product createProduct(ProductDTO productDTO) {

        if (productDTO.categoryName() == null) {
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_REQUIRED);
        }
        if (productRepository.findProductByName(productDTO.name()) != null) {
            throw new ApiException(ErrorDetails.PRODUCT_ALREADY_EXISTS);
        }

        CategoryProduct categoryProduct = categoryProductRepository.findByName(productDTO.categoryName());
        CategoryProduct defaultCategory = categoryProductRepository.findByName("Sem Categoria");

        Product product = new Product();
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setQuantity(productDTO.quantity());
        if (categoryProduct == null) {
            product.setCategoryProduct(defaultCategory);
        } else {
            product.setCategoryProduct(categoryProduct);
        }
        product.setPrice(productDTO.price());
        product.setActive(true);

        productRepository.save(product);

        return product;
    }

    public List<Product> listarProdutos(String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findAll();
        }

        List<Product> products =  productRepository.findProductByNameContainingIgnoreCase(name);
        return products;
    }

    public Product atualizarProduto(String name, UpdateProductDTO dto) {
        Product product = productRepository.findProductByName(name);

        if (product == null) {
            throw new ApiException(ErrorDetails.PRODUCT_NOT_FOUND);
        }

        if (dto.name() != null) {
            product.setName(dto.name());
        }
        if (dto.price() != null) {
            product.setPrice(dto.price());
        }
        if (dto.description() != null) {
            product.setDescription(dto.description());
        }
        if (dto.quantity() != null) {
            product.setQuantity(dto.quantity());
        }
        CategoryProduct categoryProduct = categoryProductRepository.findByName(dto.categoryName());
        if (categoryProduct == null) {
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }
        product.setCategoryProduct(categoryProduct);

        return productRepository.save(product);
    }

    public Product desativarProduto(String name) {
        Product product = productRepository.findProductByName(name);
        if (product == null) {
            throw new ApiException(ErrorDetails.PRODUCT_NOT_FOUND);
        }
        if (product.getActive() == false) {
            throw new ApiException(ErrorDetails.PRODUCT_ALREADY_DEACTIVATED);
        }
        product.setActive(false);
        return productRepository.save(product);
    }
}

