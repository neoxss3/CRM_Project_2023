/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import crm22.entity.Project;
import crm22.entity.Task;
import crm22.entity.User;
import crm22.repositories.TaskRepository;
import crm22.service.ProjectService;
import crm22.service.TaskService;
import crm22.service.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhbs
 */
@WebServlet(name = "TaskController", urlPatterns = {"/task-table", "/task-add"})
public class TaskController extends HttpServlet {

    private static final TaskService ts = new TaskService();
    private static final UserService us = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/task-table":
                List<Task> lst = new ArrayList<>();
                try {
                    boolean check = ts.getAllTasks(lst);
                    if (check) {
                        req.setAttribute("TASK_INFO", lst);
                        System.out.println("there are tasks found");
                    } else {

                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println("(Task Controller) SQL Execution Error");
                } finally {
                    req.getRequestDispatcher("task.jsp").forward(req, resp);
                }
                break;
            case "/task-add":
                HttpSession session = req.getSession();
                List<Project> listproj = (List<Project>) session.getAttribute("PROJECT_INFO");
                List<User> listusr = (List<User>) session.getAttribute("LIST_USER");
                if (listproj == null || listusr == null) {
                    if (listproj == null) {
                        listproj = new ArrayList<>();
                        ProjectService ps = new ProjectService();
                        try {
                            ps.getAllProjects(listproj);
                            session.setAttribute("PROJECT_INFO", listproj);
                        } catch (ClassNotFoundException ex) {
                            System.out.println("(Task Controller) SQL Execution Error" + ex.getLocalizedMessage());
                        }
                    }
                    if (listusr == null) {
                        listusr = new ArrayList<>();
                        UserService us = new UserService();
                        try {
                            us.getAllUser(listusr);
                            session.setAttribute("LIST_USER", listusr);
                        } catch (ClassNotFoundException | SQLException ex) {
                            System.out.println("(Task Controller) SQL Execution Error" + ex.getLocalizedMessage());
                        }
                    }
                }
                req.getRequestDispatcher("task-add.jsp").forward(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/task-add":
                String url = "task-add.jsp";
                String task_project_id = req.getParameter("task_project_id");
                String task_id = req.getParameter("task_id");
                String task_name = req.getParameter("task_name");
                String task_desc = req.getParameter("task_desc");
                String task_user = req.getParameter("task_user");
                String task_start_date = req.getParameter("task_start_date");
                String task_end_date = req.getParameter("task_end_date");
                String status_name = "OG";

                System.out.println("-----Data view-----");
                System.out.println(task_project_id);
                System.out.println(task_id);
                System.out.println(task_name);
                System.out.println(task_desc);
                System.out.println(task_user);
                System.out.println(task_start_date);
                System.out.println(task_end_date);

                try {
                    String error_message = "";
                    boolean userexist = us.checkUserExist(task_user);
                    boolean dupplicatetaskid = ts.checkDuplicatedTaskID(task_id);
                    if (userexist) {
                        if (dupplicatetaskid) {
                            error_message = "Dupplicated Task ID !";
                        } else {
                            boolean result = ts.insertTask(task_id, task_name, task_desc, task_start_date, task_end_date, task_project_id,task_user, status_name);
                            if (result) {
                                url = "index.jsp";
                            } else {
                                error_message = "Something went wrong, Please Try again";
                            }
                        }
                    } else {
                        error_message = "User is not existed";
                    }
                    if (!error_message.equals("")) {
                        req.setAttribute("TASK_INSERT_ERROR", error_message);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("(Task Controller) SQL Execution Error: " + ex.getLocalizedMessage());
                }

                req.getRequestDispatcher(url).forward(req, resp);
                break;

            default:
                break;
        }
    }

}
