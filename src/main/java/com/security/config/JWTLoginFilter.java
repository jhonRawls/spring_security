package com.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.alibaba.fastjson.JSON;
import com.security.dto.ResultDto;
import com.security.model.User;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		try {
			// JSON反序列化成 AccountCredentials
			User creds = JSON.parseObject(request.getInputStream(), User.class);
			// 返回一个验证令牌
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUserName(), creds.getPassWord()));
		} catch (Exception e) {
			throw new AuthenticationServiceException("没有权限访问");
		}
	}

//	// 验证成功后调用
//	@Override
//	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
//			Authentication auth) throws IOException, ServletException {
//		JwtTokenUtil.addAuthentication(res, auth.getName());
//		logger.info("successfulAuthentication---------------------------");
//	}

	// 验证失败后调用，这里直接灌入500错误返回，由于同一JSON返回，HTTP就都返回200了
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		// 包装响应报文
		// response.getOutputStream().println("innner error");
		ResultDto<String> result = new ResultDto<>();
		result.setCode(-9999);
		result.setMsg("not have token !!!");
		response.getWriter().println(JSON.toJSONString(result));
		// response.getOutputStream().println(JSONResult.fillResultString(500,
		// "Internal Server Error!!!", JSONObject.NULL));

	}
	
	

}
