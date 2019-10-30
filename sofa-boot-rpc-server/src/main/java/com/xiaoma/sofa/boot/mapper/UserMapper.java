package com.xiaoma.sofa.boot.mapper;

import com.xiaoma.sofa.boot.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :  xm
 * @description :
 * @date  :  2019/10/30
 */
@Mapper
public interface UserMapper {

    User findByName(String name);
}
