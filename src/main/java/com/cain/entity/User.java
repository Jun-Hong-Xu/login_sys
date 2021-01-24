package com.cain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Cain
 * @version v1.0
 * @Title: User
 * @Description: TODO
 * @date 2021/1/23 14:31
 */

@Data
// Generate the getter and setter by chain way
@Accessors(chain = true)
public class User {
    private String id;
    private String username;
    private String password;
    private String gender;
    private String status;
    private Date registerTime;
    private String email;

}
