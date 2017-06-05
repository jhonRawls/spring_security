package com.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails extends User implements UserDetails {

	//UserDetails代表了Spring Security的用户认证实体，带有用户名、密码、权限列表、过期特性等性质
	private List<UserRole> roles;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8681254901775137342L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (roles == null || roles.size() < 1) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		}
		StringBuilder commaBuilder = new StringBuilder();
		for (UserRole role : roles) {
			commaBuilder.append(role.getRole()).append(",");
		}
		String authorities = commaBuilder.substring(0, commaBuilder.length() - 1);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	public MyUserDetails(User user, List<UserRole> roles) {
		super(user);
		this.roles = roles;
	}

	@Override
	public String getPassword() {
		return super.getPassWord();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
