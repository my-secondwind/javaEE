package com.inno;

import com.inno.ConnectionManager.ConnectionManager;
import com.inno.ConnectionManager.ConnectionManagerJdbcImpl;
import com.inno.dao.UserDao;
import com.inno.dao.UserDaoJdbcImpl;
import com.inno.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        User user = new User(null, "Ivan", "1996-09-16", 15,
                "Moscow", "super@gmail.com", "second user", "asdfgh");
        UserDao userDao = new UserDaoJdbcImpl();
        userDao.addUser(user);
    }
}
