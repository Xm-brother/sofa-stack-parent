package com.xiaoma.sofa.boot.Controller;

import com.xiaoma.sofa.boot.entity.Teacher;
import com.xiaoma.sofa.boot.entity.User;
import com.xiaoma.sofa.boot.service.TeacherService;
import com.xiaoma.sofa.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private static Logger logger   = LoggerFactory.getLogger("MDC-EXAMPLE");
    private final UserService userService;
    private final TeacherService teacherService;

    @RequestMapping("getUser")
    public String getUser(@RequestParam("name") String name){
        User user = userService.findByName(name);
        return "姓名:"+user.getName()+",年龄:"+user.getAge()+",性别:"+user.getSex();
    }

    @RequestMapping("getTeacher")
    public String getUserList(){
        List<Teacher> teacherList = teacherService.getTeacherList();
       for (Teacher t: teacherList){
            System.out.println("Teacher:"+t.getName());
        }
        return "成功";
    }
}
