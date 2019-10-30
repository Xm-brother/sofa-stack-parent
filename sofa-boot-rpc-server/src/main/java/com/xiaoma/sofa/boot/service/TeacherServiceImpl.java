package com.xiaoma.sofa.boot.service;

import com.xiaoma.sofa.boot.entity.Teacher;
import com.xiaoma.sofa.boot.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public List<Teacher> getTeacherList(){
        return teacherMapper.getTeacherList();
    }
}
