package me.eumenides.service;

import me.eumenides.entity.User;

import java.util.List;

/**
 * Created by Eumenides on 2017/10/18.
 */
public interface UserService {
    List<User> findByConditions(User user);
    User findByUsername(String username);
    Integer insertNewUser(User user);
    User findByUserId(Integer userId);
}
