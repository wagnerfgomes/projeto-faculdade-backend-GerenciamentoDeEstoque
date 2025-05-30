package br.com.projeto.apigerenciamentodeestoque.service.UserUseCase;

import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetUserByNameUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByNameUseCase getUserByNameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se o usuário existe")
    void execute() {
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
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(userMock);

        User user = getUserByNameUseCase.execute("testUser");

        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    @DisplayName("Testa se retorna uma Exception quando o usuário não existe")
    void execute2() {
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            getUserByNameUseCase.execute(any(String.class));
        });

        assertEquals(ErrorDetails.USER_NOT_FOUND, exception.getErrorDetails());
    }
}