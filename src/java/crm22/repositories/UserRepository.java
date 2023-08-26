/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.repositories;

import crm22.config.MySQLConfig;
import crm22.entity.Role;
import crm22.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anhbs
 */
public class UserRepository {

    public User checkLogin(String email, String password) throws ClassNotFoundException, SQLException {
        String query = "SELECT *\n"
                + "FROM tblUsers us\n"
                + "JOIN tblRole rl ON us.role_id = rl.role_id\n"
                + "WHERE email = ? AND password = ?";
        Connection con = MySQLConfig.getConnection();
        User result = null;
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = new User();
                int id = rs.getInt("userid");
                String name = rs.getString("full_Name");

                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("role_name"));

                result.setId(id);
                result.setFullName(name);
                result.setEmail(email);
                result.setRole(role);

            }
        } catch (SQLException e) {
            System.out.println("Query Execution Error" + e.getLocalizedMessage());
        }
        return result;
    }

    public void getAllUsers(List<User> lst) throws ClassNotFoundException, SQLException {
        String query = "SELECT *\n"
                + "FROM tblUsers us\n"
                + "JOIN tblRole rl ON us.role_id = rl.role_id\n";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        User us;
        Role role;
        while (rs.next()) {
            us = new User();
            role = new Role();

            us.setId(rs.getInt("userid"));
            us.setFullName(rs.getString("full_Name"));
            us.setEmail(rs.getString("email"));

            role.setName(rs.getString("role_name"));
            us.setRole(role);
            lst.add(us);
        }
    }

    public boolean addUser(String name, String email, String pass, String address, String phone, int role) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO tblUsers "
                + "(full_Name,email,password,address,phone,role_id) "
                + "VALUES (?,?,?,?,?,?)";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, name);
        st.setString(2, email);
        st.setString(3, pass);
        st.setString(4, address);
        st.setString(5, phone);
        st.setInt(6, role);
        int res = st.executeUpdate();
        return res > 0;
    }
}
