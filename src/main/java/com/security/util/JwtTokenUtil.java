package com.security.util;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.security.dto.ResultDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	static final long EXPIRATIONTIME = 432_000_000; // 5天
	static final String SECRET = "!$Xuop@^&%x$Sci(hs_+$"; // JWT密码
	static final String TOKEN_PREFIX = "access_token"; // Token前缀
	static final String HEADER_STRING = "token";// 存放Token的Header Key
//	// JWT生成方法
//
//	public static void addAuthentication(HttpServletResponse response, String username) {
//		// 生成JWT
//		String JWT = Jwts.builder()
//				// 保存权限（角色）
//				.claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
//				// 用户名写入标题
//				.setSubject(username)
//				// 有效期设置
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
//				// 签名设置
//				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
//		// 将 JWT 写入 body
//		try {
//			response.setContentType("application/json");
//			response.setStatus(HttpServletResponse.SC_OK);
//			ResultDto<String> result=new ResultDto<>();
//			result.setResult(JWT);
//			response.getOutputStream().println(JSON.toJSONString(result));
//			response.getOutputStream().println("");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	// JWT验证方法
	public static Authentication ValidateAuthenToken(HttpServletRequest request) {
		// 从Header中拿到token
		String token = request.getHeader(HEADER_STRING);
		if (StringUtils.isNotBlank(token)) {
			// 解析 Token
			Claims claims = Jwts.parser()
					// 验签
					.setSigningKey(SECRET)
					// 去掉 Bearer
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

			// 拿用户名
			String user = claims.getSubject();
			// 得到 权限（角色）
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));

			// 返回验证令牌
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;
		}
		return null;
	}

	public String generateToken(UserDetails userDetails) {
		String oauths=StringUtils.join(userDetails.getAuthorities(), ",");
		String JWT = Jwts.builder()
				// 保存权限（角色）
				.claim("authorities",oauths)
				// 用户名写入标题
				.setSubject(userDetails.getUsername())
				// 有效期设置
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				// 签名设置
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return JWT;
	}

	String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRATIONTIME * 1000);
	}

	
}
