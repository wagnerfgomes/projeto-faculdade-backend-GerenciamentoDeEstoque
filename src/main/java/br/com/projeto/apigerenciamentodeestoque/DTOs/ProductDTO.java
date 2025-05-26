package br.com.projeto.apigerenciamentodeestoque.DTOs;
import lombok.Data;

import java.math.BigDecimal;

public record ProductDTO(
    String name,
    String description,
    BigDecimal price,
    int quantity,
    String categoryName
) {}
