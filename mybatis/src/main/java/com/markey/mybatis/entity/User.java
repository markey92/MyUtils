package com.markey.mybatis.entity;

import java.util.Date;

/**
 * @ProjectName: MyUtils
 * @Package: com.markey.mybatis.dao
 * @ClassName: User
 * @Author: markey
 * @Description:
 * @Date: 2020/8/16 21:55
 * @Version: 1.0
 */
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserIdNo() {
        return userIdNo;
    }

    public void setUserIdNo(String userIdNo) {
        this.userIdNo = userIdNo;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userAge=" + userAge +
                ", userIdNo='" + userIdNo + '\'' +
                ", userPhoneNum='" + userPhoneNum + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", userState='" + userState + '\'' +
                '}';
    }
}

