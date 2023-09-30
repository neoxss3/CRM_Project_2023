/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.repositories;

import crm22.config.MySQLConfig;
import crm22.entity.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anhbs
 */
public class RoleRepository {

    public boolean checkDupplicateID(int role_id) throws SQLException, ClassNotFoundException {
        String query = "SELECT role_id FROM tblRole WHERE role_id = ?";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, role_id);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public boolean checkDupplicateName(String role_name) throws SQLException, ClassNotFoundException {
        String query = "SELECT role_name FROM tblRole WHERE role_name = ?";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, role_name);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public boolean insertRole(int id, String name, String desc) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO tblRole VALUES (?,?,?)";
        Connection con = MySQLConfig.getConnection();

        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, id);
        st.setString(2, name);
        st.setString(3, desc);
        int test = st.executeUpdate();

        return test > 0;
    }

    public void getAllRole(List<Role> result) throws ClassNotFoundException, ClassNotFoundException {
        String query = "SELECT * FROM tblRole";
        Connection con = MySQLConfig.getConnection();
        try {
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("role_id");
                String name = rs.getString("role_name");
                String desc = rs.getString("role_desc");
                result.add(new Role(id, name, desc));
            }
        } catch (SQLException e) {
            System.out.println("Query Execution Error" + e.getLocalizedMessage());
        }
    }

    public boolean deleteRole(int id) throws ClassNotFoundException {
        String query = "DELETE FROM tblRole WHERE role_id = ?";
        Connection con = MySQLConfig.getConnection();
        int test = 0;
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            test = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Execution Error: " + e.getLocalizedMessage());
        }
        return test > 0;
    }

    public boolean UpdateRole(int id, String role_name, String role_desc) throws ClassNotFoundException {
        String query = "UPDATE tblRole SET role_name = ?, role_desc = ? WHERE role_id = ?";
        Connection con = MySQLConfig.getConnection();
        int test = 0;
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, role_name);
            st.setString(2, role_desc);
            st.setInt(3, id);
            test = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Execution Error" + e.getLocalizedMessage());
        }
        return test > 0;
    }
    public int getIDonName(String name) throws ClassNotFoundException, SQLException{
        String query = "SELECT role_id FROM tblRole WHERE role_name = ?";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        int result = -1;
        if(rs.next()){
            result = rs.getInt("role_id");
        }
        return result;
    }
}
