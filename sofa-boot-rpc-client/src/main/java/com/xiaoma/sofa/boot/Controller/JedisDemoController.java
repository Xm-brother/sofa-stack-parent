package com.xiaoma.sofa.boot.Controller;

import com.xiaoma.sofa.boot.service.JedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jedis")
@RequiredArgsConstructor
public class JedisDemoController {

    private final JedisService jedisService;

    @RequestMapping("getjedis")
    public String getJedis(){
        return jedisService.test();
    }
}
