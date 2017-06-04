package com.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.security.dao.mapper.UserMapper;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	UserMapper userMapper;
	@RequestMapping(value = "/tologin",method = {RequestMethod.GET})
	public String tologin(){
		System.out.println(JSON.toJSON(userMapper.selectByName("test")));
		return "user/login";
	}
}
