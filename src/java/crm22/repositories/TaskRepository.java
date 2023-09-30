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
import crm22.entity.Task;
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
public class TaskRepository {

    public void getAllTasks(List<Task> lst) throws SQLException, ClassNotFoundException {
        String query = "select tut.*,tu4.full_Name,tu4.role_id,tp.project_name, tt2.*,ts3.status_name \n"
                + "from tblUser_Task tut \n"
                + "join tblUsers tu4 on tut.userid = tu4.userid \n"
                + "join tblTasks tt2 on tut.task_id = tt2.task_id \n"
                + "join tblStatus ts3 on tt2.status_id = ts3.status_id \n"
                + "join tblProjects tp on tp.project_id = tt2.project_id";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        Task ts;
        Project pj;
        User user;
        Status stt;
        while (rs.next()) {
            ts = new Task();
            pj = new Project();
            stt = new Status();
            user = new User();
            user.setFullName(rs.getString("full_Name"));
            pj.setProject_id(rs.getString("project_id"));
            pj.setProject_name(rs.getString("project_name"));
            pj.setUs(user);
            stt.setStatus_name(rs.getString("status_name"));

            ts.setTask_id(rs.getString("task_id"));
            ts.setTask_name(rs.getString("task_name"));
            ts.setTask_desc(rs.getString("task_desc"));
            ts.setStarting_date(rs.getString("starting_date"));
            ts.setEnd_date(rs.getString("end_date"));
            ts.setPj(pj);
            ts.setStt(stt);

            lst.add(ts);
        }
    }

    public boolean checkDupplicateTaskID(String task_id) throws ClassNotFoundException, SQLException {
        String query = "Select task_id from tblTasks where task_id = ?";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, task_id);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public boolean insertTask(String task_id, String task_name, String task_desc, String starting_date, String end_date, String project_id, String status_id) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO tblTasks VALUES "
                + "(?,?,?,?,?,?,?)";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, task_id);
        st.setString(2, task_name);
        st.setString(3, task_desc);
        st.setString(4, starting_date);
        st.setString(5, end_date);
        st.setString(6, project_id);
        st.setString(7, status_id);
        int res = st.executeUpdate();
        return res > 0;
    }

    public boolean attachUserTask(String task_id, String user_name) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO tblUser_Task VALUES"
                + "(?,(SELECT userid FROM tblUsers WHERE full_Name = ?))";
        Connection con = MySQLConfig.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, task_id);
        st.setString(2, user_name);
        int res = st.executeUpdate();
        return res > 0;
    }
}
