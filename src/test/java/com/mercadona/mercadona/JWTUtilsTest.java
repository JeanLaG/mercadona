package com.mercadona.mercadona.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

class JWTUtilsTest {

    private JWTUtils jwtUtils;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtils = new JWTUtils();
        userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("testUser");
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtils.generateToken(userDetails);
        assertNotNull(token);
        assertEquals("testUser", jwtUtils.extractUsername(token));
    }

    @Test
    void testGenerateRefreshToken() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        String token = jwtUtils.generateRefreshToken(claims, userDetails);
        assertNotNull(token);
        assertEquals("testUser", jwtUtils.extractUsername(token));
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtils.generateToken(userDetails);
        String username = jwtUtils.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtUtils.generateToken(userDetails);
        assertTrue(jwtUtils.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenExpired() throws InterruptedException {
        String token = jwtUtils.generateToken(userDetails);
        assertFalse(jwtUtils.isTokenExpired(token));
        Thread.sleep(2000); // Optional: wait to simulate expiration if needed
    }
}
