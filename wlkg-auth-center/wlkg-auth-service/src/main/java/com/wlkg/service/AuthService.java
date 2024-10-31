package com.wlkg.service;

import com.wlkg.auth.entity.UserInfo;
import com.wlkg.auth.utils.JwtUtils;
import com.wlkg.client.UserClient;
import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.config.JwtProperties;
import com.wlkg.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties prop;

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String login(String username, String password) {
        User user = userClient.queryUser(username, password);
        try {
            if (user.getId() == null) {
                throw new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
            }
            // 生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), username), prop.getPrivateKey(), prop.getExpire());
            return token;
        } catch (Exception e) {
           // logger.error("[授权中心] 用户名或者密码有误，用户名称：{}", username, e);
            throw new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }

    }

}
