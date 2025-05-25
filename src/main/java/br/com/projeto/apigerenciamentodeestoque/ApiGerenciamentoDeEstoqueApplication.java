package br.com.projeto.apigerenciamentodeestoque;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gestão de Estoque", version="1", description="Api para gerenciamento de estoque de produtos"))
public class ApiGerenciamentoDeEstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGerenciamentoDeEstoqueApplication.class, args);
	}

}
