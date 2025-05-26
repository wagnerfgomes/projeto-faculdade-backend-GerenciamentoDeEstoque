package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterCategoryProductDto;
import br.com.projeto.apigerenciamentodeestoque.DTOs.UpdateCategoryProductDto;
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
public class CategoryProductService {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    public CategoryProduct createCategoryProduct(RegisterCategoryProductDto dto) {
        if (categoryProductRepository.findByName(dto.name()) != null) {
            throw new ApiException(ErrorDetails.PRODUCT_ALREADY_EXISTS);
        }
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setName(dto.name());
        categoryProduct.setDescription(dto.description());

        categoryProductRepository.save(categoryProduct);
        return categoryProduct;
    }


    public List<CategoryProduct> listCategoryProducts(String name) {
        if (name == null || name.isEmpty()) {
            return categoryProductRepository.findAll();
        }

        List<CategoryProduct> categoryProductList =  categoryProductRepository.findProductByNameContainingIgnoreCase(name);

        return categoryProductList;
    }

    public CategoryProduct categoryByName(String name) {
        CategoryProduct categoryProduct = categoryProductRepository.findByName(name);

        if (categoryProduct == null) {
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }

        return categoryProduct;
    }

    public CategoryProduct updateCategoryProduct(String name, UpdateCategoryProductDto dto) {

        if(dto.description() == null) {
            throw new ApiException(ErrorDetails.EMPTY_FIELDS);
        }

        CategoryProduct categoryProduct = categoryProductRepository.findByName(name);
        if (categoryProduct == null) {
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }
        categoryProduct.setDescription(dto.description());
        categoryProductRepository.save(categoryProduct);
        return categoryProduct;
    }

    public void deleteCategoryProduct(String name) {

        CategoryProduct categoryProduct = categoryProductRepository.findByName(name);
        CategoryProduct noCategoryProduct = categoryProductRepository.findByName("Sem Categoria");

        List<Product> products = productRepository.findByCategoryProduct(categoryProduct);

        products.forEach(product -> product.setCategoryProduct(noCategoryProduct));

        if (categoryProduct == null) {
            throw new ApiException(ErrorDetails.CATEGORY_PRODUCT_NOT_FOUND);
        }

        categoryProductRepository.delete(categoryProduct);
    }
}
