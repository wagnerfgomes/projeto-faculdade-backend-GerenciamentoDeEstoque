package br.com.projeto.apigerenciamentodeestoque.repository;

import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, UUID> {

    CategoryProduct findByName(String name);

    List<CategoryProduct> findByNameContainingIgnoreCase(String name);
}
