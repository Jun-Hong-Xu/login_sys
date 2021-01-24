package com.cain.service;

import com.cain.dao.UserDao;
import com.cain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Cain
 * @version v1.0
 * @Title: UserServiceImpl
 * @Description: TODO
 * @date 2021/1/23 23:16
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        // verify the user exist or not
        User byUsername = userDao.findByUsername(user.getUsername());
        if (byUsername == null){
            //1. The status of the user
            user.setStatus("OK");
            //2. Set the register time of the user
            user.setRegisterTime(new Date());
            userDao.create(user);
        }else{
            throw new RuntimeException("Username has been used.");
        }

    }

    @Override
    public User login(User user) {
        //1.Find user by username
        User byUsername = userDao.findByUsername(user.getUsername());
        if (byUsername!=null){
            // 2.Match the password
            if (byUsername.getPassword().equals(user.getPassword())){
                return byUsername;
            }else{
                throw new RuntimeException("Wrong password.");
            }
        }else{
            throw new RuntimeException("User do not exist.");
        }
    }
}
