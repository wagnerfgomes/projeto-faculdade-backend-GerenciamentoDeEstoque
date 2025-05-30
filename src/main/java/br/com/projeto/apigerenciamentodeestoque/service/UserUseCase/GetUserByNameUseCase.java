package br.com.projeto.apigerenciamentodeestoque.service.UserUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetUserByNameUseCase {

    @Autowired
    private UserRepository userRepository;

    public User execute(String username){
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            log.error("usuário {} foi buscado na base, mas não foi encontrado", username);
            throw new ApiException(ErrorDetails.USER_NOT_FOUND);
        }
        return user;
    }
}
