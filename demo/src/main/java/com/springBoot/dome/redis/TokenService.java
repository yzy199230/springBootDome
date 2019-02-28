package com.springBoot.dome.redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import nl.bitwalker.useragentutils.UserAgent;

@Service("tokenService")
public class TokenService {

	@Autowired
    private RedisUtil redisUtil;
	
	//生成token(格式为token:设备-加密的用户名-时间-六位随机数)
    public String generateToken(String userAgentStr, String username) {
        StringBuilder token = new StringBuilder("token:");
        //设备
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        if (userAgent.getOperatingSystem().isMobileDevice()) {
            token.append("MOBILE-");
        } else {
            token.append("PC-");
        }
        //加密的用户名
        token.append(DigestUtils.md5Hex(username) + "-");
        //时间
        token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
        //六位随机字符串
        token.append(new Random().nextInt(999999 - 111111 + 1) + 111111 );
        System.out.println("token-->" + token.toString());
        return token.toString();
    }
    
    public String getUserToken(String userAgentStr) {
    	StringBuilder token = new StringBuilder("userToken:");
    	//设备
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        if (userAgent.getOperatingSystem().isMobileDevice()) {
            token.append("MOBILE-");
        } else {
            token.append("PC-");
        }
        String uuid = UUID.randomUUID().toString();
        token.append(uuid);
        token.append(new Random().nextInt(999999 - 111111 + 1) + 111111 );
        token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
        System.out.println("userToken-->" + token.toString());
        return token.toString();
    }
 
    //把token存到redis中
    public void save(String sessionId , String token) {
    	redisUtil.set(sessionId, token, (long) 60);
    }
    public String get(String token) {
    	String result = redisUtil.get(token);
    	return result;
    }

}
