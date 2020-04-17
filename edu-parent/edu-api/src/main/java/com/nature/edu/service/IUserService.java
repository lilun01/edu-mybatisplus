package com.nature.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nature.Response;
import com.nature.edu.vo.UserVO;

/**
 * 用户相关业务类
 *
 * @author wangck
 * @date 2019/7/25
 */
public interface IUserService {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public Response<Boolean> addUser(UserVO user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    public Response<Boolean> modifyUser(UserVO user);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    public Response<Boolean> deleteUser(String userId);

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    public Response<UserVO> getUser(String userId);


    /**
     * 分页查询用户
     * @param searchName 支持根据姓名模糊搜索
     * @param page 分页参数
     * @return
     */
    public Response<Page<UserVO>> getUserPage(String searchName,Page<UserVO> page);

}
