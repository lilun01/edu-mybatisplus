package com.nature.edu.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nature.Response;
import com.nature.edu.service.IUserService;
import com.nature.edu.vo.UserVO;

/**
 * @Title: UserController.java
 * @Description: TODO(描述)
 * @author lilun
 * @date 2020-04-16 10:01:04 
 * @version 1.0
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/user/add")
    public Response<Boolean> addUser(@RequestBody UserVO user) {
        if (user == null) {
            logger.error("添加用户时，用户信息不能为空'");
            return Response.failResult("用户信息不能为空");
        }
        String personName = user.getPersonName();
        if (StringUtils.isBlank(personName)) {
            logger.error("添加用户时，personName不能为空'");
            return Response.failResult("用户姓名不能为空");
        }
        return userService.addUser(user);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("/user/modify")
    public Response<Boolean> modifyUser(UserVO user) {
        if (user == null) {
            logger.error("修改用户时，用户信息不能为空'");
            return Response.failResult("用户信息不能为空");
        }
        String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            logger.error("修改用户时，userId不能为空'");
            return Response.failResult("用户Id不能为空");
        }
        return userService.modifyUser(user);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @GetMapping("/user/delete")
    public Response<Boolean> deleteUser(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("删除用户时，userId不能为空'");
            return Response.failResult("用户Id不能为空");
        }
        return userService.deleteUser(userId);
    }

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/info")
    public Response<UserVO> getUser(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("查询用户信息时，userId不能为空'");
            return Response.failResult("用户Id不能为空");
        }
        return userService.getUser(userId);
    }

    /**
     * 分页查询用户列表
     * @param searchName 可为空
     * @param pageNo 可为空 默认为1
     * @param pageSize 可为空 默认为10
     * @return
     */
    @PostMapping("/user/page")
    public Response<Page<UserVO>> getUserPage(String searchName, String pageNo,String pageSize) {
        Page<UserVO> page = new Page<>();
        if(StringUtils.isNotBlank(pageNo)){
            page.setCurrent(Long.parseLong(pageNo));
        }
        if(StringUtils.isNotBlank(pageSize)){
            page.setSize(Long.parseLong(pageSize));
        }
        return userService.getUserPage(searchName,page);
    }
}
