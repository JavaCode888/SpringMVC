package com.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.constant.Constant;

/**
 * 管理Token
 * GaryZheng
 *
 */
public class TokenMgr {
	
	public static SecretKey generalKey() {
		try {
			byte[] encodedKey = Constant.JWT_SECERT.getBytes("UTF-8");
			SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length,
					"AES");
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 签发JWT
	 * @param id  jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
	 * @param issue jwt签发者
	 * @param userID jwt所面向的用户
	 * @param ttlMillis 有效期,单位毫秒
	 * @return token
	 * @throws Exception
	 */
	public static String createJWT(String id,String issue, String userID, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setId(id)
				.setIssuer(issue)
				.setSubject(userID)
				.setIssuedAt(now)
				.signWith(signatureAlgorithm, secretKey);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate);
		}
		return builder.compact();
	}
	

	public static CheckResult validateJWT(String token) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
			claims = parseJWT(token);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(Constant.JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	
	public static Claims parseJWT(String token) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody();
	}
}