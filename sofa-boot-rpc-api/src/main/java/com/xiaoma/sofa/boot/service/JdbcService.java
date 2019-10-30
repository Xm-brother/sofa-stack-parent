package com.xiaoma.sofa.boot.service;

import com.xiaoma.sofa.boot.entity.User;

import java.util.List;

public interface JdbcService {

    List<User> getUserList();
}
