package com.controller;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constant.Constant;
import com.jwt.TokenMgr;
import com.util.ResponseData;
@Controller
@RequestMapping("/users")
public class JSONController {
    @Autowired  
	private  HttpServletRequest request;  
    
    
	@PostMapping("/login")
	@ResponseBody
	public  ResponseData login(@RequestParam String userName, @RequestParam String password) {
		ResponseData responseData = new ResponseData();
		try {
			System.out.println("-----login   请求json数据--------");
			String token = TokenMgr.createJWT(UUID.randomUUID().toString(), Constant.JWT_ISSUE, userName, Constant.JWT_TTL);
			System.out.println(userName);
			System.out.println(token);
			responseData.setCode(Constant.RESCODE_SUCCESS);
			responseData.setMsg("success");
			responseData.getData().put("auth_token", token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseData;
	}
	
	@PostMapping("/updatePassword")
	@ResponseBody
	public  ResponseData updatePassword(@RequestParam String oldPassword,@RequestParam String newPassword) {
		ResponseData responseData = new ResponseData();
		try {
			String userName = TokenMgr.parseJWT(request.getHeader("auth_token")).getSubject();
			System.out.println(userName+ "  -----updatePassword 请求json数据--------");
		} catch (Exception e) {
			responseData.setCode(Constant.RESCODE_SUCCESS);
			responseData.setMsg("success");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseData;
	}
}