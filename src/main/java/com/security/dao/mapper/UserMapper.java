package com.security.dao.mapper; 
import org.apache.ibatis.annotations.Mapper;

import com.security.model.UserInfo;

@Mapper
public interface UserMapper {
	UserInfo selectByName(String userName);
}
