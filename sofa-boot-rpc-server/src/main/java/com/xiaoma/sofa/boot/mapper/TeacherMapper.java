package com.xiaoma.sofa.boot.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaoma.sofa.boot.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author :  xm
 * @description :
 * @date  :  2019/10/30
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<Teacher> getTeacherList();
}
