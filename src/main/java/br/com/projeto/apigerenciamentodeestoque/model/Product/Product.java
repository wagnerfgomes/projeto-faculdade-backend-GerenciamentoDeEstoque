package br.com.projeto.apigerenciamentodeestoque.model.Product;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryProduct categoryProduct;

}
