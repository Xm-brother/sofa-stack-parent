package com.xiaoma.sofa.boot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -2961008786235375158L;

    private int id;

    private String name;

    private String age;

    private String sex;
}
