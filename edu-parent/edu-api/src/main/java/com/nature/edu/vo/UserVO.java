package com.nature.edu.vo;

import com.nature.NatureObject;

import java.io.Serializable;

/**
 * @author wangck
 * @date 2019/7/25
 */
public class UserVO extends NatureObject {

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 账号
     */
    private String userNo;
    /**
     * 登录名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userHead;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String personName;
    /**
     * 姓名全拼音
     */
    private String pinyName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPinyName() {
        return pinyName;
    }

    public void setPinyName(String pinyName) {
        this.pinyName = pinyName;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userId='" + userId + '\'' +
                ", userNo='" + userNo + '\'' +
                ", userName='" + userName + '\'' +
                ", userHead='" + userHead + '\'' +
                ", nickName='" + nickName + '\'' +
                ", personName='" + personName + '\'' +
                ", pinyName='" + pinyName + '\'' +
                '}';
    }
}
