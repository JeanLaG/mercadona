package com.mercadona.mercadona.service;

import com.mercadona.mercadona.dto.ReqRes;
import com.mercadona.mercadona.entity.User;
import com.mercadona.mercadona.repository.UsersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceTest {

    @Mock
    private UsersRepo usersRepo;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_UserExists() {
        ReqRes registrationRequest = new ReqRes();
        registrationRequest.setEmail("test@example.com");
        
        when(usersRepo.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        
        ReqRes response = usersService.register(registrationRequest);
        
        assertEquals(400, response.getStatusCode());
        assertEquals("Email already exists", response.getMessage());
    }

    @Test
    void testRegister_UserCreatedSuccessfully() {
        ReqRes registrationRequest = new ReqRes();
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password");
        
        User user = new User();
        user.setId(1);
        
        when(usersRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(usersRepo.save(any(User.class))).thenReturn(user);
        
        ReqRes response = usersService.register(registrationRequest);
        
        assertEquals(200, response.getStatusCode());
        assertEquals("User Saved Successfully", response.getMessage());
        assertEquals(user, response.getUser());
    }

    @Test
    void testGetUsersById_UserFound() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        
        when(usersRepo.findById(userId)).thenReturn(Optional.of(user));
        
        ReqRes response = usersService.getUsersById(userId);
        
        assertEquals(200, response.getStatusCode());
        assertEquals("Users with id '1' found successfully", response.getMessage());
        assertEquals(user, response.getUser());
    }

    @Test
    void testDeleteUser_UserExists() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(usersRepo.findById(userId)).thenReturn(Optional.of(user));

        ReqRes response = usersService.deleteUser(userId);

        assertEquals(200, response.getStatusCode());
        assertEquals("User deleted successfully", response.getMessage());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        int userId = 1;
        User updatedUser = new User();

        when(usersRepo.findById(userId)).thenReturn(Optional.empty());

        ReqRes response = usersService.updateUser(userId, updatedUser);

        assertEquals(404, response.getStatusCode());
        assertEquals("User not found for update", response.getMessage());
    }
}
