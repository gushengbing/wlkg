package com.wlkg.user.controller;

import com.wlkg.user.pojo.User;
import com.wlkg.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api("用户中心接口")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    @ApiOperation(value = "验证用户数据接口，返回boolean", notes = "验证用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", required = true, value = "用户数据"),
            @ApiImplicitParam(name = "type", required = true, value = "数据类型")
    })
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
       // System.out.println(data);
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    @PostMapping("/code")
    @ApiOperation(value = "发送验证码接口", notes = "发送验证码")
    @ApiImplicitParam(name = "phone", required = true, value = "用户手机号")
    public ResponseEntity<Void> sendVerifyCode(String phone) {
        Boolean boo = userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register") // Valid用户实体类接受参数 验证框架
    @ApiOperation(value = "用户注册接口", notes = "用户注册",httpMethod = "POST")
    @ApiImplicitParam(name = "code", required = true, value = "验证码")
    public ResponseEntity<Void> register(@Valid  @ApiParam(value = "用户注册对象", required = true,name = "user") User user, @RequestParam("code") String code) {
        System.out.println("注册user:"+user);
        userService.register(user, code);
        return ResponseEntity.ok(null);
    }
    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    @ApiOperation(value = "用户查询接口", notes = "用户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "用户名"),
            @ApiImplicitParam(name = "password", required = true, value = "密码")
    })
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = userService.queryUser(username, password);
        return ResponseEntity.ok(user);
    }

}
