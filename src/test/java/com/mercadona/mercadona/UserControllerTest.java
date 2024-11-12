package com.mercadona.mercadona.controller;

import com.mercadona.mercadona.dto.ReqRes;
import com.mercadona.mercadona.entity.User;
import com.mercadona.mercadona.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        ReqRes request = new ReqRes();
        ReqRes response = new ReqRes();
        when(usersService.register(request)).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.register(request);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).register(request);
    }

    @Test
    void testLogin() {
        ReqRes request = new ReqRes();
        ReqRes response = new ReqRes();
        when(usersService.login(request)).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.login(request);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).login(request);
    }

    @Test
    void testRefreshToken() {
        ReqRes request = new ReqRes();
        ReqRes response = new ReqRes();
        when(usersService.refreshToken(request)).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.refreshToken(request);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).refreshToken(request);
    }

    @Test
    void testGetAllUsers() {
        ReqRes response = new ReqRes();
        when(usersService.getAllUsers()).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserByID() {
        int userId = 1;
        ReqRes response = new ReqRes();
        when(usersService.getUsersById(userId)).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.getUserByID(userId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).getUsersById(userId);
    }

    @Test
    void testUpdateUser() {
        int userId = 1;
        User request = new User();
        ReqRes response = new ReqRes();
        when(usersService.updateUser(userId, request)).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.updateUser(userId, request);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).updateUser(userId, request);
    }

    @Test
    void testGetMyProfile() {
        String email = "test@example.com";
        ReqRes response = new ReqRes();
        response.setStatusCode(200); // Ensure status code is set

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(usersService.getMyInfo(email)).thenReturn(response);
        SecurityContextHolder.setContext(securityContext);

        ResponseEntity<ReqRes> result = userController.getMyProfile();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode()); // Expecting 200 OK
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).getMyInfo(email);
    }

    @Test
    void testDeleteUser() {
        int userId = 1;
        ReqRes response = new ReqRes();
        when(usersService.deleteUser(userId)).thenReturn(response);

        ResponseEntity<ReqRes> result = userController.deleteUSer(userId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(usersService, times(1)).deleteUser(userId);
    }
}
