/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.repositories;

import crm22.config.MySQLConfig;
import crm22.entity.Project;
import crm22.entity.Role;
import crm22.entity.Status;
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
public class ProjectRepository {

    public void getAllProjects(List<Project> lst) throws ClassNotFoundException {
        String query = "select tp.*, tu.full_Name ,ts.status_name ,ts.status_name, tr.role_name from tblProjects tp "
                + "inner join tblUsers tu on tp.manager_id = tu.userid "
                + "inner join tblRole tr on tu.role_id = tr.role_id "
                + "inner join tblStatus ts on tp.status_id = ts.status_id ";
        Connection con = MySQLConfig.getConnection();
        try {
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String id = rs.getString("project_id");
                String name = rs.getString("project_name");
                String startdate = rs.getString("starting_date");
                String enddate = rs.getString("end_date");

                Project pj = new Project();
                User us = new User();
                Role rl = new Role();
                Status stt = new Status();

                rl.setName(rs.getString("role_name"));
                us.setFullName(rs.getString("full_Name"));
                us.setRole(rl);
                stt.setStatus_name(rs.getString("status_name"));

                pj.setProject_id(id);
                pj.setProject_name(name);
                pj.setStarting_date(startdate);
                pj.setEnd_date(enddate);
                pj.setUs(us);
                pj.setStt(stt);

                lst.add(pj);
            }
        } catch (SQLException e) {
            System.out.println("Query Execution Error" + e.getLocalizedMessage());
        }
    }

    public boolean insertProject(String project_id, String project_name, String project_start, String project_end, int manager_id, String status_id) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO tblProjects VALUES (?,?,?,?,?,?)";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, project_id);
        st.setString(2, project_name);
        st.setString(3, project_start);
        st.setString(4, project_end);
        st.setInt(5, manager_id);
        st.setString(6, status_id);
        int row = st.executeUpdate();
        return row > 0;
    }

    public boolean attachUserProject(String project_id, int manager_id) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO tblUser_Project VALUES (?,?)";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, project_id);
        st.setInt(2, manager_id);
        int row = st.executeUpdate();
        return row > 0;
    }

    public boolean checkDupplicatedProjectId(String projectID) throws ClassNotFoundException, SQLException {
        String query = "SELECT project_id FROM tblProjects WHERE project_id = ?";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, projectID);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }
}
