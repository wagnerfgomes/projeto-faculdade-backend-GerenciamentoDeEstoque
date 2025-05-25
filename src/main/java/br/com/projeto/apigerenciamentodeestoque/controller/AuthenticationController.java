package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.AuthenticationDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.TokenResponse;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.model.User.UserRole;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import br.com.projeto.apigerenciamentodeestoque.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", method = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Usuário ou senha incorretos")
            }
    )
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        String token = authenticationService.validateLogin(authenticationDTO);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro de usuário", method = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "409", description = "Usuário já existe")
            }
    )
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO){
        authenticationService.registerNewUser(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
