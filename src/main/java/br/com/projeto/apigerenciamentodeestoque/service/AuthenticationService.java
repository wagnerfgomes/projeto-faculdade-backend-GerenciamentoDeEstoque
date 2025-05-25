package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.AuthenticationDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.model.User.UserRole;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TokenService tokenService;

    public String validateLogin(AuthenticationDTO dto){
        User user = userRepository.findUserByUsername(dto.username());
        if ( user == null) {
            log.error("usuário {} tentou logar, mas não foi encontrado", dto.username());
            throw new ApiException(ErrorDetails.USER_NOT_FOUND);
        }
        if (!user.isActive()){
            log.error("usuário {} tentou logar, mas está desativado", dto.username());
            throw new ApiException(ErrorDetails.USER_NOT_ACTIVE);
        }
        var userNamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        try{
            Authentication authenticate = authenticationManager.authenticate(userNamePassword);
            String token = tokenService.generateToken((User) authenticate.getPrincipal());
            log.info("usuário {} logou com sucesso", dto.username());
            return token;
        }catch (BadCredentialsException e){
            log.error("usuário {} tentou logar com as credências erradas", dto.username());
            throw new ApiException(ErrorDetails.ACESS_DENIED);
        }
    }

    public void registerNewUser(RegisterDTO dto) {
        if(dto.username() == null || dto.password() == null) {
            throw new ApiException(ErrorDetails.EMPTY_FIELDS);
        }
        if (userRepository.findUserByUsername(dto.username()) != null) {
            log.error("usuário {} tentou se registrar pela segunda vez", dto.username());
            throw new ApiException(ErrorDetails.USER_EXIST);
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(encryptedPassword);
        user.setActive(true);
        UserRole commonRole = userRoleRepository.findUserRoleByRoleName(UserRole.Roles.COMMON);
        user.setRole(commonRole);
        log.info("usuário {} se registrou com sucesso", dto.username());
        userRepository.save(user);
    }
}
