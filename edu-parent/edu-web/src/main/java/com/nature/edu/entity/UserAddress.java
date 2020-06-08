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
@TableName("user_address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
     * 用户Id，主键
     */
    @TableId("address_id")
    private String addressId;
    
    private String address;


    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
