package com.xiaoma.sofa.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath*:rpc-starter-example.xml" })
public class SofaBootRpcDemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(
                SofaBootRpcDemoApplication.class, args);
    }
}
