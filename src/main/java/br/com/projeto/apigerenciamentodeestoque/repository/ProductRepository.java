package br.com.projeto.apigerenciamentodeestoque.repository;

import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findProductByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryProduct(CategoryProduct categoryProduct);
}
