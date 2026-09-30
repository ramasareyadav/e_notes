package com.e_notes.service;

import com.e_notes.model.User;

public interface JwtService {

    public String generateToken(User user);
}
