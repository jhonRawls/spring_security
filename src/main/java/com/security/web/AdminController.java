package com.security.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//只有 管理员权限才可以操作
@PreAuthorize("hasRole('ADMIN')") 
@RequestMapping("/admin")
@Controller
public class AdminController {
	@RequestMapping(value = "/toOrderList",method = {RequestMethod.GET})
	public String toOrderList(){
		return "admin/orderList";
	}
	
	@RequestMapping(value = "/toUserList",method = {RequestMethod.GET})
	public String toUserList(){
		return "admin/userList";
	}
}
