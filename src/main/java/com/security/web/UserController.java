package com.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/user")
@Controller
public class UserController {
	@RequestMapping(value = "/tologin",method = {RequestMethod.GET})
	public String tologin(){
		return "user/login";
	}
}
