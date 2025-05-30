package br.com.projeto.apigerenciamentodeestoque.service.UserUseCase;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UserDto;
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

class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se realiza a atualização do usuário")
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

        User updatedUser = updateUserUseCase.execute(new UserDto("testUser", "newPassword", false));

        assertNotNull(updatedUser);
        assertEquals("testUser", updatedUser.getUsername());
        assertNotEquals(new BCryptPasswordEncoder().encode("testPassword"), updatedUser.getPassword());
        assertEquals(false, updatedUser.isActive());
    }

    @Test
    @DisplayName("Testa se retorna uma Exception quando o usuário não existe")
    void execute2() {
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            updateUserUseCase.execute(new UserDto("testUser", "newPassword", true));
        });

        assertEquals(ErrorDetails.USER_NOT_FOUND, exception.getErrorDetails());
    }

    @Test
    @DisplayName("Testa se retorna uma Exception quando a senha já foi utilizada")
    void execute3() {
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

        ApiException exception = assertThrows(ApiException.class, () -> {
            updateUserUseCase.execute(new UserDto("testUser", "testPassword", true));
        });

        assertEquals(ErrorDetails.PASSWORD_ALREADY_USED, exception.getErrorDetails());
    }

}