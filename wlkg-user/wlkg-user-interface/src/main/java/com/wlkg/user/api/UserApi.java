package com.wlkg.user.api;

import com.wlkg.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface UserApi {

    @GetMapping("/check/{data}/{type}")
    public Boolean checkUserData(@PathVariable("data") String data, @PathVariable("type") Integer type);
    @PostMapping("/code")
    public Void sendVerifyCode(String phone);
    @PostMapping("/register") // Valid用户实体类接受参数 验证框架
    public Void register(@Valid User user, @RequestParam("code") String code);
    @GetMapping("/query")
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
