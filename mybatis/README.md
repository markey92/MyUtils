使用mybatis步骤

# 引入mybatis依赖

```
<!--mybatis依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.2.0</version>
</dependency>
```

# mybatis配置项

```
# mybatis配置
mybatis:
  configuration:
    map-underscore-to-camel-case: true # 数据库映射字段进行驼峰转化
```

# 定义实体类

```
package com.markey.mybatis.entity;

import java.util.Date;

public class User {
    private int userId;
    private String userName;
    private String userSex;
    private int userAge;
    private String userIdNo;
    private String userPhoneNum;
    private String userEmail;
    private Date createTime;
    private Date modifyTime;
    private String userState;

    //省略getter setter
}
```

# 定义mapper

```
package com.markey.mybatis.dao;

import com.markey.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> list();
}
```

