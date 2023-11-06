package com.bhargav.dao;

import com.bhargav.db.Database;
import com.bhargav.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final Connection con;

    public UserDao() {

        con = Database.getConnection();
    }
    private String selectSQL = "SELECT id, username, password FROM auth WHERE username=? and password=?";
    private String RegisterSQL="INSERT INTO auth(username,password)VALUES(?,?);";
    public User loginUser(String username, String password) {
        User user = null;
        try {
            PreparedStatement ps = con.prepareStatement(selectSQL);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public void register(String username, String password) {
        try {
            PreparedStatement rt= con.prepareStatement(RegisterSQL);
            rt.setString(1, username);
            rt.setString(2, password);
            rt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
