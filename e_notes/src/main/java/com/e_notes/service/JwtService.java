package com.e_notes.service;

import com.e_notes.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String generateToken(User user);

    String extractUsername(String token);

    public Boolean validateToken(String token, UserDetails userDetails);
}
