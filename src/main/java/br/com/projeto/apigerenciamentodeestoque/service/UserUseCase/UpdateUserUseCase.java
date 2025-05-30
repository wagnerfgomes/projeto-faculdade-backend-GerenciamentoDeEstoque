package br.com.projeto.apigerenciamentodeestoque.service.UserUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UserDto;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public User execute(UserDto dto){
        User user = userRepository.findUserByUsername(dto.username());
        if(user == null){
            log.error("usuário {} foi buscado na base, mas não foi encontrado", dto.username());
            throw new ApiException(ErrorDetails.USER_NOT_FOUND);
        }
        if (dto.password() != null){
            if (!new BCryptPasswordEncoder().matches(dto.password(), user.getPassword())) {
                String encodePassword = new BCryptPasswordEncoder().encode(dto.password());
                user.setPassword(encodePassword);
            }else {
                log.error("usuário {} tentou alterar a senha por uma senha já utilizada antes", dto.username());
                throw new ApiException(ErrorDetails.PASSWORD_ALREADY_USED);
            }
        }
        user.setActive(dto.active());
        userRepository.save(user);
        return user;
    }
}
