package br.com.projeto.apigerenciamentodeestoque.model.Product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "category_product")
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    @Column(name = "category_description")
    private String description;

}
