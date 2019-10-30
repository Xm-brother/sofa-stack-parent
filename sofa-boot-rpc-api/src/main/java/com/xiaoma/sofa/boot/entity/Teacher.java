package com.xiaoma.sofa.boot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable {

    private static final long serialVersionUID = 5126127104902324486L;

    private int id;

    private String name;

    private String sex;
}
