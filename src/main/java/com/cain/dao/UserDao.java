package com.cain.dao;

import com.cain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper //Generate the UserDao object
//Solve the warning of the could not autowired(DK the reason)
@Repository
public interface UserDao {
    void create(User user);
    User findByUsername(String username);
    void update(User user);

}
