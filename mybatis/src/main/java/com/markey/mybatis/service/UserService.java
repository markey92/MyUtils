package com.markey.mybatis.service;

import com.markey.mybatis.dao.UserMapper;
import com.markey.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.mybatis.service
 * @ClassName: UserService
 * @Author: markey
 * @Description:
 * @Date: 2020/8/16 22:06
 * @Version: 1.0
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUser() {
        return userMapper.list();
    }
}
