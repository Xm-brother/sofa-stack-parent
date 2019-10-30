/**
 * 服务注册启动类
 */
package com.xiaoma.sofa.boot;

import com.xiaoma.sofa.boot.service.PersonServiceZookeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({ "classpath*:rpc-starter-example.xml" })
public class SofaBootRpcClientDemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SofaBootRpcClientDemoApplication.class,args);
        //注册中心为zookeeper
        PersonServiceZookeeper personBolt = (PersonServiceZookeeper) applicationContext
                .getBean("personServiceZookeeperBolt");
        /*PersonServiceZookeeper personRest = (PersonServiceZookeeper) applicationContext
                .getBean("personServiceZookeeperRest");
        PersonServiceZookeeper personDubbo = (PersonServiceZookeeper) applicationContext
                .getBean("personServiceZookeeperDubbo");*/
        System.out.println(personBolt.sayName("boltrpc"));
        /*System.out.println(personRest.sayName("restrpc"));
        System.out.println(personDubbo.sayName("dubborpc"));*/

        //注册中心为SOFARegistry
        /*PersonServiceRest personServiceRest = (PersonServiceRest)applicationContext
                .getBean("personServiceRest");
        PersonServiceBolt personReferenceBolts = (PersonServiceBolt)applicationContext
                .getBean("personReferenceBolt");
        System.out.println(personServiceRest.sayName("restrpc"));
        System.out.println(personReferenceBolts.sayName("boltrpc"));*/

    }
}
