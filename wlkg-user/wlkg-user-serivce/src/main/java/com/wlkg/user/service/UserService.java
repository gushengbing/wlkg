package com.wlkg.user.service;


import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.utils.CodecUtils;
import com.wlkg.common.utils.NumberUtils;
import com.wlkg.user.mapper.UserMapper;
import com.wlkg.user.pojo.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

    public Boolean checkData(String data, Integer type) {
        User usr = new User();
        switch (type) {
            case 1:
                usr.setUsername(data);
                break;
            case 2:
                usr.setPhone(data);
                break;
            default:
                return null;
        }
      /*  List<User> users = userMapper.select(usr);
        return users.get(0) != null;*/

        User user = userMapper.selectOne(usr);
        return user == null;
    }

    public Boolean sendVerifyCode(String phone) {
        //生产随机验证码
        String code = NumberUtils.generateCode(6);
        try {
            //将验证码保存到redis里
            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            //发送消息
            Map<String, String> map = new HashMap<>();
            map.put("phone", phone);
            map.put("code", code);
            amqpTemplate.convertAndSend("wlkg.sms.exchange", "sms.verify.code", map);
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public void register(User user, String code) {
        User usr = new User();
        //校验验证码
        String prefix = KEY_PREFIX + user.getPhone();
        String redisCode = redisTemplate.opsForValue().get(prefix);
        if (!code.equals(redisCode)) {
            throw new WlkgException(ExceptionEnums.INVALID_VERFIY_CODE);
        }
        //生成盐
        String salt = CodecUtils.generateSalt();
        usr.setSalt(salt);

        //生成加密密码
        usr.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        usr.setUsername(user.getUsername());
        usr.setPhone(user.getPhone());
        usr.setCreated(new Date());
        System.out.println("注册的用户:" + usr);
        //添加用户
        if (userMapper.insert(usr) == 1) {
            redisTemplate.delete(prefix);
        }
    }



    public User queryUser(String username, String password) {
        User u = new User();
        u.setUsername(username);
        User user = userMapper.selectOne(u);
        if (user == null) {
            throw  new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new WlkgException(ExceptionEnums.INVALID_USERNAME_PASSWORD);
        }
        return user;
    }
}
