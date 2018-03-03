package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.constant.Constant;
import com.jwt.CheckResult;
import com.jwt.TokenMgr;
import com.util.Json2StringUtil;
import com.util.ResponseData;

/**
 * @author GaryZheng
 *
 */
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		// 请求URL
		String url = request.getRequestURI().toString();
		ResponseData responseData = new ResponseData();
		// 如果是登录则不拦截开始
		if (url.contains("/users/login")) {
			return true;
		}

		String tokenStr = request.getHeader("auth_token");
		if (tokenStr == null || tokenStr.equals("")) {
			responseData.setCode(Constant.RESCODE_NOAUTH);
			responseData.setMsg("no login");
			response.getWriter().append(Json2StringUtil.objectToJsonStr(responseData));
			response.getWriter().flush();
			response.getWriter().close();
			return false;
		}

		// 验证JWT的签名，返回CheckResult对象
		CheckResult checkResult = TokenMgr.validateJWT(tokenStr);
		if (checkResult.isSuccess()) {
			return true;
		} else {
			switch (checkResult.getErrCode()) {
			// 签名过期
			case Constant.JWT_ERRCODE_EXPIRE:
				responseData.setCode(Constant.JWT_ERRCODE_EXPIRE);
				responseData.setMsg("token expired");
				response.getWriter().append(
						Json2StringUtil.objectToJsonStr(responseData));
				response.getWriter().flush();
				response.getWriter().close();
				break;
			// 签名验证不通过
			case Constant.JWT_ERRCODE_FAIL:
				responseData.setCode(Constant.JWT_ERRCODE_FAIL);
				responseData.setMsg("token auth failed");
				response.getWriter().append(
						Json2StringUtil.objectToJsonStr(responseData));
				response.getWriter().flush();
				response.getWriter().close();
				break;
			default:
				break;
			}
			return false;
		}
	}

}
