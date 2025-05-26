package br.com.projeto.apigerenciamentodeestoque.DTOs;

import br.com.projeto.apigerenciamentodeestoque.model.Product.CategoryProduct;
import lombok.Data;

import java.math.BigDecimal;


public record UpdateProductDTO(String name, Integer quantity, BigDecimal price, String description, String categoryName)
{}