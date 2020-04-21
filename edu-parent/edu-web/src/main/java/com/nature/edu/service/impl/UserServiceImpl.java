package com.nature.edu.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nature.Response;
import com.nature.edu.dao.BasUserMapper;
import com.nature.edu.entity.BasUser;
import com.nature.edu.service.IUserService;
import com.nature.edu.vo.UserVO;
import com.xuanner.seq.utils.UUIDUtils;

/**
 * @author wangck
 * @date 2019/7/25
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BasUserMapper basUserMapper;

    @Override
    public Response<Boolean> addUser(UserVO user) {
        if (user == null) {
            logger.error("添加用户时，用户信息不能为空'");
            return Response.failResult("用户信息不能为空");
        }
        String personName = user.getPersonName();
        if (StringUtils.isBlank(personName)) {
            logger.error("添加用户时，personName不能为空'");
            return Response.failResult("用户姓名不能为空");
        }
        BasUser basUser = new BasUser();
        basUser.setUserId(UUIDUtils.uuid());
        basUser.setUserNo(user.getUserNo());
        basUser.setUserName(user.getUserName());
        basUser.setPersonName(user.getPersonName());
        Integer count = basUserMapper.insert(basUser);
        if (count > 0) {
            logger.info("添加用户成功，用户信息：{}", basUser);
            return Response.successResult("添加用户成功", true);
        } else {
            logger.error("添加用户失败，用户信息：{}", basUser);
            return Response.failResult("添加用户失败", false);
        }

    }

    @Override
    @CacheInvalidate(name="nature.edu.user",key="#user.userId")
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
        BasUser basUser = new BasUser();
        basUser.setUserId(userId);
        basUser.setUserNo(user.getUserNo());
        basUser.setUserName(user.getUserName());
        basUser.setPersonName(user.getPersonName());
        Integer count = basUserMapper.updateById(basUser);
        if (count > 0) {
            logger.info("修改用户成功，用户信息：{}", basUser);
            return Response.successResult("修改用户成功", true);
        } else {
            logger.error("修改用户失败，用户信息：{}", basUser);
            return Response.failResult("修改用户失败", false);
        }
    }

    @Override
    @CacheInvalidate(name="nature.edu.user",key="#userId")
    public Response<Boolean> deleteUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("删除用户时，userId不能为空'");
            return Response.failResult("用户Id不能为空");
        }
        Integer count = basUserMapper.deleteById(userId);
        if (count > 0) {
            logger.info("删除用户成功，用户Id：{}", userId);
            return Response.successResult("删除用户成功", true);
        } else {
            logger.error("删除用户失败，用户id：{}", userId);
            return Response.failResult("删除用户失败", false);
        }
    }

    @Override
    @Cached(name="nature.edu.user",key="#userId",expire = 120, cacheType = CacheType.REMOTE)
    public Response<UserVO> getUser(String userId) {
        if (StringUtils.isBlank(userId)) {
            logger.error("查询用户信息时，userId不能为空'");
            return Response.failResult("用户Id不能为空");
        }
        BasUser basUser = basUserMapper.selectById(userId);
         //BasUser basUser = basUserMapper.selectOne(new QueryWrapper<BasUser>().lambda().eq(true, BasUser::getPersonName, "lisi"));
        
        UserVO user = new UserVO();
        if (basUser != null) {
            user.setUserId(basUser.getUserId());
            user.setUserNo(basUser.getUserNo());
            user.setUserName(basUser.getUserName());
            user.setUserHead(basUser.getUserHead());
            user.setPersonName(basUser.getPersonName());
            user.setPinyName(basUser.getPinyName());
            logger.info("查询用户成功，用户Id：{}", userId);
            return Response.successResult("查询用户成功", user);
        } else {
            logger.error("查询用户失败，用户Id：{}", userId);
            return Response.failResult("查询用户失败", user);
        }
    }

    @Override
    public Response<Page<UserVO>> getUserPage(String searchName, Page<UserVO> page) {
        List<UserVO> list = basUserMapper.selectUserPage(searchName, page);
        page.setRecords(list);
        return Response.successResult("查询成功", page);
    }
}
