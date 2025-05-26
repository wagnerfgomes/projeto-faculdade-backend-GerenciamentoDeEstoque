package br.com.projeto.apigerenciamentodeestoque.DTOs;
import lombok.Data;

import java.math.BigDecimal;

public record ProductDTO(
    int id,
    String name,
    String description,
    BigDecimal price,
    int quantity,
    String categoryName
) {}
