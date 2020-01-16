package com.inno.dao.old;

import com.inno.dao.UserDao;
import com.inno.pojo.User;

import java.util.Collection;

public class UserJdbcAdapter implements UserDao {

    AnotherUserJdbcImpl anotherUserJdbc;

    public UserJdbcAdapter(AnotherUserJdbcImpl anotherUserJdbc) {
        this.anotherUserJdbc = anotherUserJdbc;
    }

    @Override
    public boolean addUser(User user) {
        return anotherUserJdbc.adding(user);
    }

    @Override
    public User getUserById(Integer id) {
        return anotherUserJdbc.getting(id);
    }

    @Override
    public boolean updateUserById(User user) {
        return anotherUserJdbc.updating(user);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return anotherUserJdbc.deleting(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return anotherUserJdbc.gettingAll();
    }

    @Override
    public boolean verifyUser(User user) {
        return anotherUserJdbc.verifying(user);
    }
}
