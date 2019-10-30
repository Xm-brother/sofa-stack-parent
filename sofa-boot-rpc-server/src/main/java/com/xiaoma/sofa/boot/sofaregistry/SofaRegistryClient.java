/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiaoma.sofa.boot.sofaregistry;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.xiaoma.sofa.boot.service.HelloService;

/**
 * use sofa-registry client demo
 */
public class SofaRegistryClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(SofaRegistryClient.class);

    public static void main(String[] args) {
        /**
         * 运行时项目引入依赖
         <dependency>
             <groupId>com.alipay.sofa</groupId>
             <artifactId>registry-client-all</artifactId>
             <version>5.2.0</version>
         </dependency>
         */
        RegistryConfig registryConfig = new RegistryConfig()
                //REGISTRY_PROTOCOL_SOFA
            .setProtocol("sofa")
            .setAddress("127.0.0.1:9603");

        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
            .setInterfaceId(HelloService.class.getName())
            .setRegistry(registryConfig)
            .setProtocol("bolt")
            .setConnectTimeout(10 * 1000);

        HelloService helloService = consumerConfig.refer();
        LOGGER.warn("started at pid {}", RpcRuntimeContext.PID);

        try {
            while (true) {
                try {
                    System.out.println(helloService.sayHello("world",18));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}