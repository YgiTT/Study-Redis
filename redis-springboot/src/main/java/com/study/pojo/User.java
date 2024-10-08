package com.study.pojo;

import java.io.Serializable;

/**
 * @Author Ryan Yan
 * @Since 2021/2/4 17:30
 */
public class User implements Serializable {

    private  String name;

    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
