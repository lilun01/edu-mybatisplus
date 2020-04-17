package com.nature.edu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nature.edu.entity.BasUser;
import com.nature.edu.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangck
 * @date 2019/8/6
 */
public interface BasUserMapper extends BaseMapper<BasUser> {

    /**
     * 分页查询用户
     * @param searchName
     * @param page
     * @return
     */
    public List<UserVO> selectUserPage(@Param("searchName")String searchName, Page<UserVO> page);

}
