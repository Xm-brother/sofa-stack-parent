package com.xiaoma.sofa.boot.service;

import com.xiaoma.sofa.boot.entity.User;
import com.xiaoma.sofa.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author :  xm
 * @description :
 * @date  :  2019/10/30
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @param
     * @param name
     * @return  com.xiaoma.sofa.boot.entity.User
     **/
    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
