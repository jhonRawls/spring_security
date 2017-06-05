package com.security.dao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.security.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {

	@Autowired
	UserMapper userMapper;

	@Test
	public void test() {
		System.out.println(JSON.toJSON(userMapper.selectByName("test")));
	}

	@Test
	public void testInsert() {
		User user=new User("jhon","12434545",1);
		userMapper.insertUser(user);
		System.out.println(JSON.toJSON(userMapper.selectByName("test")));
	}
}
