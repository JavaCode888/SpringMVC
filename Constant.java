package com.constant;

public class Constant {
	
	public static final int RESCODE_SUCCESS = 200;// 成功
	public static final int RESCODE_EXCEPTION = 201;// system error
	public static final int RESCODE_NOLOGIN = 202; //未登陆状态
	public static final int RESCODE_NOAUTH = 203;// 无操作权限
	public static final int RESCODE_LOGINEXPIRE = 204;// 登录过期

	/**
	 * token
	 */
	public static final int JWT_ERRCODE_EXPIRE = 205;// Token过期
	public static final int JWT_ERRCODE_FAIL = 206;// 验证不通过

	/**
	 * jwt
	 */
	public static final String JWT_ISSUE = "support-team"; // jwt签发者
	public static final String JWT_SECERT = "46cc793c53dc451b8a4fe2cd0bb00847";// 密匙
	public static final long JWT_TTL = 12 * 60 * 60 * 1000;// token有效时间,单位毫秒

}