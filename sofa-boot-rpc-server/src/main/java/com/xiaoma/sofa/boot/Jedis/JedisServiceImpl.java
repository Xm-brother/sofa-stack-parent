package com.xiaoma.sofa.boot.Jedis;


import com.xiaoma.sofa.boot.service.JedisService;
import com.xiaoma.sofa.boot.utils.CacheUtils;

/**
 * @author :  xm
 * @description :
 * @date  :  2019/10/30
 */
public class JedisServiceImpl implements JedisService {

    @Override
    public String test(){
        String key = "demo";
        CacheUtils.updateEntity(key,"abc",1800 );

        String a  = CacheUtils.getEntity(key,String.class);
        System.out.println("reids:"+a);
        return a;
    }
}
