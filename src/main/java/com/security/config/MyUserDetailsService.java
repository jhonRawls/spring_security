package com.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.dao.mapper.UserMapper;
import com.security.dao.mapper.UserRoleMapper;
import com.security.model.MyUserDetails;
import com.security.model.User;
import com.security.model.UserRole;
//自定义用户服务
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserMapper userMapper;// 需要有用户中心服务

	@Autowired
	UserRoleMapper userRoleMapper;// 需要有用户中心服务

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User userinfo;
		try {
			userinfo = userMapper.selectByName(username);// 数据库匹配账户信息
		} catch (Exception e) {
			throw new UsernameNotFoundException("user select fail");
		}

		if (userinfo == null) {
			throw new UsernameNotFoundException("no user found");
		} else {
			try {
				List<UserRole> roles = userRoleMapper.selectByUserId(userinfo.getUserId());
				return new MyUserDetails(userinfo, roles);
			} catch (Exception e) {
				throw new UsernameNotFoundException("user role select fail");
			}
		}

		// Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		// // 权限 如果登录了基友 user权限，未登录访客权限
		// if (userinfo != null) {// 数据库存在给与user权限
		// List<UserRole> roles =
		// userRoleMapper.selectByUserId(userinfo.getUserId());
		// grantedAuthorities.add(new SimpleGrantedAuthority("User"));
		//
		// } else {
		// grantedAuthorities.add(new SimpleGrantedAuthority("Guest"));
		// }
		// return new MyUserDetails(userinfo, roles);
	}

}
