package com.example.a95795.thegreenplant.custom;

import java.util.Date;

/**
 * Created by 95795 on 2019-04-17.
 */

public class User {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer userSex;

    private String userCall;

    private Date userFirstjob;

    private Integer userWork;

    private Integer userWorkshop;

    private String userPassword;

    private String userNum;

    private String userNumberId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserCall() {
        return userCall;
    }

    public void setUserCall(String userCall) {
        this.userCall = userCall == null ? null : userCall.trim();
    }

    public Date getUserFirstjob() {
        return userFirstjob;
    }

    public void setUserFirstjob(Date userFirstjob) {
        this.userFirstjob = userFirstjob;
    }

    public Integer getUserWork() {
        return userWork;
    }

    public void setUserWork(Integer userWork) {
        this.userWork = userWork;
    }

    public Integer getUserWorkshop() {
        return userWorkshop;
    }

    public void setUserWorkshop(Integer userWorkshop) {
        this.userWorkshop = userWorkshop;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    public String getUserNumberId() {
        return userNumberId;
    }

    public void setUserNumberId(String userNumberId) {
        this.userNumberId = userNumberId == null ? null : userNumberId.trim();
    }
}