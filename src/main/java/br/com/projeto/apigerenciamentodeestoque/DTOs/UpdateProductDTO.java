package br.com.projeto.apigerenciamentodeestoque.DTOs;

import lombok.Data;

import java.math.BigDecimal;


public record UpdateProductDTO(String name,Integer quantity, BigDecimal price)
{}