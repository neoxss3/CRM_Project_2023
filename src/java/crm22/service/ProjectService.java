/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.service;

import crm22.entity.Project;
import crm22.repositories.ProjectRepository;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author anhbs
 */
public class ProjectService {

    private static final ProjectRepository pr = new ProjectRepository();

    public boolean getAllProjects(List<Project> lst) throws ClassNotFoundException {
        pr.getAllProjects(lst);
        return lst.size() > 0;
    }
    public boolean insertProject(String project_id, String project_name, String project_start, String project_end, int manager_id, String status_id) throws SQLException, ClassNotFoundException{
        boolean checkProject = pr.insertProject(project_id, project_name, project_start, project_end, manager_id, status_id);
        boolean checkUser = pr.attachUserProject(project_id, manager_id);
        return checkProject && checkUser;
    }
    public boolean checkDupplicatedPID(String projectId) throws ClassNotFoundException, SQLException{
        return pr.checkDupplicatedProjectId(projectId);
    }
}
