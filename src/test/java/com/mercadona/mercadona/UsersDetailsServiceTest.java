package com.mercadona.mercadona.service;

import com.mercadona.mercadona.entity.User;
import com.mercadona.mercadona.repository.UsersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersDetailsServiceTest {

    @Mock
    private UsersRepo usersRepo;

    @InjectMocks
    private UsersDetailsService usersDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "test@example.com";
        User user = new User();
        user.setEmail(username);
        
        when(usersRepo.findByEmail(username)).thenReturn(Optional.of(user));
        
        UserDetails userDetails = usersDetailsService.loadUserByUsername(username);
        
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

}
