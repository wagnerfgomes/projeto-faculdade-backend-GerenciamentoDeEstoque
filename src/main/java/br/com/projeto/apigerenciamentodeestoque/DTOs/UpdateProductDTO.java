package br.com.projeto.apigerenciamentodeestoque.DTOs;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class UpdateProductDTO {
    private String name;
    private Integer quantity;
    private BigDecimal price;

}