package com.course.model;

import lombok.Data;

/**
 * Created by apple on 2020/3/11.
 */
@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private String sex;
    private int age;
    private int permission;
}
