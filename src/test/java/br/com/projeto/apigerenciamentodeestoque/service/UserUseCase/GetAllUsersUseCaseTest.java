package br.com.projeto.apigerenciamentodeestoque.service.UserUseCase;

import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.model.User.UserRole;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetAllUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUsersUseCase getAllUsersUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se retorna todos os usuários")
    void allUsers() {
        UUID uuid = UUID.randomUUID();
        User userMock = new User();
        userMock.setId(uuid);
        userMock.setUsername("testUser");
        var encryptedPassword = new BCryptPasswordEncoder().encode("testPassword");
        userMock.setPassword(encryptedPassword);
        userMock.setActive(true);
        UserRole mockRole = new UserRole();
        mockRole.setRoleName(UserRole.Roles.COMMON);
        userMock.setRole(mockRole);
        when(userRepository.findAll()).thenReturn(List.of(userMock));

        List<User> users = getAllUsersUseCase.execute();

        assertNotNull(users);
        assertEquals(1, users.size());
    }
}