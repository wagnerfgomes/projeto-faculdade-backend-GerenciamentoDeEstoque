package br.com.projeto.apigerenciamentodeestoque.controller;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UserDto;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRoleRepository;
import br.com.projeto.apigerenciamentodeestoque.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserService userService;


    @GetMapping
    @Operation(summary = "Buscar usuário por username", method = "GET")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    public ResponseEntity<User> getUserById(@RequestParam(name = "username") String username){
        User user = userService.userByName(username);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar todos os usuários", method = "GET")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuários não encontrados")
            }
    )

    public ResponseEntity<List<User>> getAllUsers(){
        var users = userService.allUsers();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/update")
    @Operation(summary = "Atualizar usuário", method = "PUT")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Senha já utilizada antes"),
            }
    )
    public ResponseEntity updateUser(@RequestBody UserDto dto){
        User user = userService.updateUser(dto);
        return ResponseEntity.ok().body(user);
    }
}
