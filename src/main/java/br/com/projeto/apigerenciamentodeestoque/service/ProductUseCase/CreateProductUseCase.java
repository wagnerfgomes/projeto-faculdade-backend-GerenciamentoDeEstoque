package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.ProductDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    public Product execute(ProductDTO productDTO) {
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
}
