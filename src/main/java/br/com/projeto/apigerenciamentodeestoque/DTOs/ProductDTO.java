package br.com.projeto.apigerenciamentodeestoque.DTOs;
import lombok.Data;

import java.math.BigDecimal;



@Data
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

}
