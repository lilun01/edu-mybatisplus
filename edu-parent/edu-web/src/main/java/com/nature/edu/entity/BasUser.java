package com.nature.edu.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wangck
 * @since 2019-08-07
 */
@TableName("bas_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
     * 用户Id，主键
     */
    @TableId("user_id")
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
     * 头像
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


    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
