package com.security.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	static final long EXPIRATIONTIME = 432_000_000; // 5天
	static final String SECRET = "!$Xuop@^&%x$Sci(hs_+$"; // JWT密码
	static final String TOKEN_PREFIX = "access_token"; // Token前缀
	static final String HEADER_STRING = "token";// 存放Token的Header Key

	// 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
	// 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
	// 本例中，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询
	// 但简单验证的话，你可以采用直接验证token是否合法来避免昂贵的数据查询

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
		String oauths = StringUtils.join(userDetails.getAuthorities(), ",");
		String JWT = Jwts.builder()
				// 保存权限（角色）
				.claim("authorities", oauths)
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
