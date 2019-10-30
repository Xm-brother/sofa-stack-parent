package com.xiaoma.sofa.boot.Controller;

import com.xiaoma.sofa.boot.entity.User;
import com.xiaoma.sofa.boot.service.JdbcService;
import com.xiaoma.sofa.boot.service.PersonServiceZookeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/rpc")
public class SampleRestController{

    private static Logger logger   = LoggerFactory.getLogger("MDC-EXAMPLE");
    /*@Resource*/
    @Autowired
    private PersonServiceZookeeper personServiceZookeeper;
    @Autowired
    private JdbcService jdbcService;

    @RequestMapping("helloword")
    public String getRest(){
       String helloword = personServiceZookeeper.sayName("hello word");
        return helloword;
    }

    @RequestMapping("jdbcconnent")
    public String getUserList(){
        List<User> userlist =  jdbcService.getUserList();
        for (User user :userlist) {
            System.out.println(user.getName());
        }
        return "查询成功";
    }
}
