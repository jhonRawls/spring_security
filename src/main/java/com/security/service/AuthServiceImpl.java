package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dao.mapper.UserMapper;
import com.security.model.User;
import com.security.util.JwtTokenUtil;

@Service
public class AuthServiceImpl implements AuthService {
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil;
	private UserMapper userMapper;

	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			JwtTokenUtil jwtTokenUtil, UserMapper userRepository) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userMapper = userRepository;
	}

	// 注册
	@Override
	public User register(User userToAdd) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = userToAdd.getPassWord();
		userToAdd.setPassWord(encoder.encode(rawPassword));
		userMapper.insertUser(userToAdd);
		return userToAdd;
	}

	// 登录
	@Override
	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
		final Authentication authentication = authenticationManager.authenticate(upToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		return token;
	}

	// 刷新token
	@Override
	public String refresh(String oldToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
