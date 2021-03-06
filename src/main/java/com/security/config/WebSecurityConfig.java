package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider provider;// 自定义验证

	 @Autowired
	    private JwtAuthenticationEntryPoint unauthorizedHandler;
	 
	// 设置 HTTP 验证规则
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/",
		// "/home").permitAll().anyRequest().authenticated().and().formLogin()
		// .loginPage("/login").permitAll().and().logout().permitAll();

		// 关闭csrf验证
		http.csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

        // 基于token，所以不需要session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 对请求进行认证
				.authorizeRequests()
				// 所有 / 的所有请求 都放行
				.antMatchers("/").permitAll()
				// 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
//				// 所有 /login 的POST请求 都放行
				.antMatchers("/user/**").permitAll().anyRequest().authenticated();
//				// 权限检查
////				.antMatchers("/hello").hasAuthority("AUTH_WRITE")
//				// 角色检查
////				.antMatchers("/admin").hasRole("ADMIN")
//				// 所有请求需要身份认证
//				.anyRequest().authenticated().and()
//				// 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
//				.addFilterBefore(new JWTLoginFilter("/admin/*", authenticationManager()),UsernamePasswordAuthenticationFilter.class)
//				// 添加一个过滤器验证其他请求的Token是否合法
//				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		
//		  // 禁用缓存
//		http.headers().cacheControl();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
		// auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		// 使用自定义身份验证组件
		auth.authenticationProvider(provider);
	}
	
	// 装载BCrypt密码编码器
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    public JWTAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationFilter();
//        return new JwtAuthenticationTokenFilter();
    }
}


