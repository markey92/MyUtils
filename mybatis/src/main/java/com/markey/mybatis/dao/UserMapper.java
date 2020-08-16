package com.markey.mybatis.dao;

import com.markey.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.mybatis.dao
 * @ClassName: UserMapper
 * @Author: markey
 * @Description:
 * @Date: 2020/8/16 22:04
 * @Version: 1.0
 */
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> list();
}
