package com.security.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.security.model.MyUserDetails;

@Component
// 自定义认证
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MyUserDetailsService userService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取认证的用户名 & 密码
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		MyUserDetails user = (MyUserDetails) userService.loadUserByUsername(username);
		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}
		// 加密过程在这里体现
		
		if (!encoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);

		// // 认证逻辑
		// if (username.equals("admin") && password.equals("123456")) {
		// // 这里设置权限和角色
		// ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		// authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		// authorities.add(new GrantedAuthorityImpl("AUTH_WRITE"));
		// // 生成令牌
		// Authentication auth = new
		// UsernamePasswordAuthenticationToken(username, password, authorities);
		// return auth;
		// } else {
		// throw new BadCredentialsException("密码错误~");
		// }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public static void main(String [] args){
//		Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("123");
//		md5PasswordEncoder.encodePassword("myPassword", "fds");
		System.out.println(result);
		System.out.println(encoder.matches("123", result));
	}
}
