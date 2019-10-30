package com.xiaoma.sofa.boot.service;


/**
 * PersonServiceZookeeperImpl
 */
public class PersonServiceZookeeperImpl implements PersonServiceZookeeper {
    public String sayName(String string) {
        return "hi " + string + "!";
    }
}
