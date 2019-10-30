package com.xiaoma.sofa.boot.service;

import com.xiaoma.sofa.boot.entity.User;

public interface UserService {
    User findByName(String name);
}
