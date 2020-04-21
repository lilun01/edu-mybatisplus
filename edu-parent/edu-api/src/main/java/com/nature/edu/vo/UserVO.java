package com.nature.edu.vo;

import com.nature.NatureObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Title: UserVO.java
 * @Description: TODO(描述)
 * @author lilun
 * @date 2020-04-21 03:01:21 
 * @version 1.0
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class UserVO extends NatureObject {

	private static final long serialVersionUID = 1L;
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

}
