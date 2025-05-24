package br.com.projeto.apigerenciamentodeestoque.service;

import br.com.projeto.apigerenciamentodeestoque.DTOs.AuthenticationDTO;
import br.com.projeto.apigerenciamentodeestoque.DTOs.RegisterDTO;
import br.com.projeto.apigerenciamentodeestoque.exception.ApiException;
import br.com.projeto.apigerenciamentodeestoque.exception.ErrorDetails;
import br.com.projeto.apigerenciamentodeestoque.model.User.User;
import br.com.projeto.apigerenciamentodeestoque.model.User.UserRole;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRepository;
import br.com.projeto.apigerenciamentodeestoque.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa o login com sucesso")
    void validateLogin1() {
        AuthenticationDTO AuthDTOMock = new AuthenticationDTO("testUser", "testPassword");
        UUID uuid = UUID.randomUUID();
        User userMock = new User();
        userMock.setId(uuid);
        userMock.setUsername("testUser");
        userMock.setPassword("testPassword");
        userMock.setActive(true);
        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRole.Roles.COMMON);
        userMock.setRole(userRole);
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(userMock);
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                new UsernamePasswordAuthenticationToken(AuthDTOMock.username(), AuthDTOMock.password());
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getPrincipal()).thenReturn(userMock);
        when(authenticationManager.authenticate(usernamePasswordAuthToken)).thenReturn(mockAuth);
        when(tokenService.generateToken(userMock)).thenReturn("mockedToken");

        String token = authenticationService.validateLogin(AuthDTOMock);

        assertEquals("mockedToken", token);
    }

    @Test
    @DisplayName("Testa o login com Excepxtion de usuario não encontrado")
    void validateLogin2(){
        AuthenticationDTO AuthDTOMock = new AuthenticationDTO("testUser", "testPassword");
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.validateLogin(AuthDTOMock);
        });

        assertEquals(ErrorDetails.USER_NOT_FOUND, exception.getErrorDetails());
    }

    @Test
    @DisplayName("Testa o login com Excepxtion de usuario inativo")
    void validateLogin3(){
        AuthenticationDTO AuthDTOMock = new AuthenticationDTO("testUser", "testPassword");
        UUID uuid = UUID.randomUUID();
        User userMock = new User();
        userMock.setId(uuid);
        userMock.setUsername("testUser");
        userMock.setPassword("testPassword");
        userMock.setActive(false);
        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRole.Roles.COMMON);
        userMock.setRole(userRole);
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(userMock);

        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.validateLogin(AuthDTOMock);
        });
        verify(authenticationManager, never()).authenticate(any());

        assertEquals(ErrorDetails.USER_NOT_ACTIVE, exception.getErrorDetails());
    }

    @Test
    @DisplayName("Testa o login com Excepxtion de credenciais inválidas")
    void validateLogin4(){
        AuthenticationDTO AuthDTOMock = new AuthenticationDTO("testUser", "testPassword");
        UUID uuid = UUID.randomUUID();
        User userMock = new User();
        userMock.setId(uuid);
        userMock.setUsername("testUser");
        userMock.setPassword("testPassword");
        userMock.setActive(true);
        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRole.Roles.COMMON);
        userMock.setRole(userRole);
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(userMock);
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                new UsernamePasswordAuthenticationToken(AuthDTOMock.username(), AuthDTOMock.password());
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getPrincipal()).thenReturn(userMock);
        when(authenticationManager.authenticate(usernamePasswordAuthToken))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.validateLogin(AuthDTOMock);
        });

        assertEquals(ErrorDetails.ACESS_DENIED, exception.getErrorDetails());
    }

    @Test
    @DisplayName("Testa o registro de um novo usuário com sucesso")
    void registerNewUser1() {
        RegisterDTO registerDTOMock = new RegisterDTO("testUser", "testPassword");
        UUID uuid = UUID.randomUUID();
        User userMock = new User();
        userMock.setId(uuid);
        userMock.setUsername("testUser");
        var emcryptedPassword = new BCryptPasswordEncoder().encode("testPassword");
        userMock.setPassword(emcryptedPassword);
        userMock.setActive(true);
        UserRole mockRole = new UserRole();
        mockRole.setRoleName(UserRole.Roles.COMMON);
        userMock.setRole(mockRole);
        when(userRoleRepository.findUserRoleByRoleName(UserRole.Roles.COMMON)).thenReturn(mockRole);
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        authenticationService.registerNewUser(registerDTOMock);
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("testUser", userMock.getUsername());
        assertNotEquals("testPassword", userMock.getPassword());
        assertEquals(true, userMock.isActive());
        assertEquals(UserRole.Roles.COMMON, userMock.getRole().getRoleName());
    }

    @Test
    @DisplayName("Testa o registro de um novo usuário com Excepxtion credenciais vazias")
    void registerNewUser2() {
        RegisterDTO registerDTOMock = new RegisterDTO(null, null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.registerNewUser(registerDTOMock);
        });

        assertEquals(ErrorDetails.EMPTY_FIELDS, exception.getErrorDetails());
    }

    @Test
    @DisplayName("Testa o registro de um novo usuário com Excepxtion usuário já existe")
    void registerNewUser3() {
        RegisterDTO registerDTOMock = new RegisterDTO("testUser", "testPassword");UUID uuid = UUID.randomUUID();
        User userMock = new User();
        userMock.setId(uuid);
        userMock.setUsername("testUser");
        var emcryptedPassword = new BCryptPasswordEncoder().encode("testPassword");
        userMock.setPassword(emcryptedPassword);
        userMock.setActive(true);
        UserRole mockRole = new UserRole();
        mockRole.setRoleName(UserRole.Roles.COMMON);
        userMock.setRole(mockRole);
        when(userRepository.findUserByUsername(any(String.class))).thenReturn(userMock);

        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.registerNewUser(registerDTOMock);
        });

        assertEquals(ErrorDetails.USER_EXIST, exception.getErrorDetails());
    }
}