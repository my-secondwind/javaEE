package com.inno.dao;

import com.inno.pojo.User;

import java.util.Collection;

public interface UserDao {
    boolean addUser(User user);

    User getUserById(Integer id);

    boolean updateUserById(User user);

    boolean deleteUserById(Integer id);

    Collection<User> getAllUsers();

    boolean verifyUser(User user);
}
