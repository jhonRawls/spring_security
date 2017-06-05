package com.security.dao.mapper; 
import org.apache.ibatis.annotations.Mapper;

import com.security.model.User;

@Mapper
public interface UserMapper {
	User selectByName(String userName);
	int insertUser(User user);
}
