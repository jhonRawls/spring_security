package com.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.security.model.User;
import com.security.service.AuthService;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/tologin", method = { RequestMethod.GET })
	public String tologin() {
		return "user/login";
	}

	@RequestMapping(value = "/toreg", method = { RequestMethod.GET })
	public String toRegister() {
		return "user/register";
	}

	@RequestMapping(value = "/register", method = { RequestMethod.POST }, consumes = "application/json")
	@ResponseBody
	public User register(@RequestBody User addedUser) {
		String name = addedUser.getUserName();
		String pwd = addedUser.getPassWord();
		System.out.println("reg name " + name + " pwd " + pwd);
		return authService.register(addedUser);
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST }, consumes = "application/json")
	@ResponseBody
	public JSONObject createAuthenticationToken(@RequestBody User user) {
		final String token = authService.login(user.getUserName(), user.getPassWord());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		return jsonObject;
	}

}
