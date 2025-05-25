package br.com.projeto.apigerenciamentodeestoque.repository;

import br.com.projeto.apigerenciamentodeestoque.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findByname(String name);

    List<Product> findByNameContainingIgnoreCase(String name);
}
