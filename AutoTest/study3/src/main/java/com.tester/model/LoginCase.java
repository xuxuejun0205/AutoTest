package com.tester.model;

import lombok.Data;

/**
 * Created by apple on 2020/3/13.
 */
@Data
public class LoginCase {
    private int id;
    private String userName;
    private String password;
    private String expected;
}

