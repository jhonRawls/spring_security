package com.security.dao.mapper; 
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.security.model.UserRole;

@Mapper
public interface UserRoleMapper {
	List<UserRole> selectByUserId(int userId);
}
