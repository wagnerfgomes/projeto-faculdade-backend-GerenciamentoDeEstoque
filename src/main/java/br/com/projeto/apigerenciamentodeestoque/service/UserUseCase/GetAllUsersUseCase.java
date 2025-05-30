package br.com.projeto.apigerenciamentodeestoque.service.UserUseCase;

import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GetAllUsersUseCase {

    @Autowired
    private UserRepository userRepository;

    public List<User> execute(){
        List<User> users = userRepository.findAll();
        return users;
    }
}
