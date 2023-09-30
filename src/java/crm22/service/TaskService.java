/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.service;

import crm22.entity.Task;
import crm22.repositories.TaskRepository;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anhbs
 */
public class TaskService {
    private static final TaskRepository tp = new TaskRepository();
    public boolean getAllTasks(List<Task> lst) throws SQLException, ClassNotFoundException{
        tp.getAllTasks(lst);
        return lst.size() > 0;
    }
    public boolean insertTask(String task_id,String task_name, String task_desc, String starting_date, String end_date, String project_id,String task_user, String status_id) throws ClassNotFoundException, SQLException{
        boolean checkTask = tp.insertTask(task_id, task_name, task_desc, starting_date, end_date, project_id, status_id);
        boolean checkUser = tp.attachUserTask(task_id, task_user);
        return checkTask && checkUser;
    }
    public boolean checkDuplicatedTaskID(String task_id) throws ClassNotFoundException, SQLException{
        return tp.checkDupplicateTaskID(task_id);
    }
}
