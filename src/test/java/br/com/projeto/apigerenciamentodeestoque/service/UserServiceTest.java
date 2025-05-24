package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.UserDto;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.model.User.UserRole;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import jakarta.inject.Inject;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa se o usuário existe")
    void userByName() {
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

        User user = userService.userByName("testUser");

        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    @DisplayName("Testa se retorna uma Exception quando o usuário não existe")
    void userByName2() {
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.userByName(any(String.class));
        });

        assertEquals(ErrorDetails.USER_NOT_FOUND, exception.getErrorDetails());
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

        List<User> users = userService.allUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("Testa se realiza a atualização do usuário")
    void updateUser() {
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

        User updatedUser = userService.updateUser(new UserDto("testUser", "newPassword", false));

        assertNotNull(updatedUser);
        assertEquals("testUser", updatedUser.getUsername());
        assertNotEquals(new BCryptPasswordEncoder().encode("testPassword"), updatedUser.getPassword());
        assertEquals(false, updatedUser.isActive());
    }

    @Test
    @DisplayName("Testa se retorna uma Exception quando o usuário não existe")
    void updateUser2() {
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.updateUser(new UserDto("testUser", "newPassword", true));
        });

        assertEquals(ErrorDetails.USER_NOT_FOUND, exception.getErrorDetails());
    }

    @Test
    @DisplayName("Testa se retorna uma Exception quando a senha já foi utilizada")
    void updateUser3() {
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
            userService.updateUser(new UserDto("testUser", "testPassword", true));
        });

        assertEquals(ErrorDetails.PASSWORD_ALREADY_USED, exception.getErrorDetails());
    }
}