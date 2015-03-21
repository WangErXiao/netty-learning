package com.yao.netty.transferobj;

import java.io.Serializable;

/**
 * Created by root on 15-3-7.
 */
public class Dog implements Serializable {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
