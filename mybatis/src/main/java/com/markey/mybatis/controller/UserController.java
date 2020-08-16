package com.markey.mybatis.controller;

import com.markey.mybatis.entity.User;
import com.markey.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.mybatis.controller
 * @ClassName: UserController
 * @Author: markey
 * @Description:
 * @Date: 2020/8/16 22:07
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public ResponseEntity getAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
