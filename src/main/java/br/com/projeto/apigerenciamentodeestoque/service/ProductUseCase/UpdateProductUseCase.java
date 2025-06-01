package br.com.projeto.apigerenciamentodeestoque.service.ProductUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateProductDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import br.com.projeto.apigerenciamentodeestoque.repository.CategoryProductRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    public Product execute(String name, UpdateProductDTO dto) {
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
}
