package com.security.service;

import com.security.model.User;

public interface AuthService {
	User register(User userToAdd);

	String login(String username, String password);

	String refresh(String oldToken);
}
