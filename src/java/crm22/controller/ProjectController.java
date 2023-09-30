/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.controller;

import crm22.entity.Project;
import crm22.entity.User;
import crm22.service.ProjectService;
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
@WebServlet(name = "ProjectController", urlPatterns = {"/project", "/project-add"})
public class ProjectController extends HttpServlet {

    private static final ProjectService ps = new ProjectService();
    private static final UserService us = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/project":
                resp.sendRedirect("404.jsp");
                break;
            case "/project-add":
                String url = "project-add.jsp";
                String project_id = req.getParameter("project_id");
                String project_name = req.getParameter("project_name");
                int manager_id = Integer.parseInt(req.getParameter("project_mgr"));
                String project_start = req.getParameter("project_start");
                String project_end = req.getParameter("project_end");
                String status_id = "OG";

                System.out.println("----- Data View -----");
                System.out.println(project_id);
                System.out.println(project_name);
                System.out.println(project_start);
                System.out.println(project_end);
                System.out.println("Manager ID: " + manager_id);

                try {
                    String error_message = "";
                    boolean checkDupplicate = ps.checkDupplicatedPID(project_id);
                    if (checkDupplicate) {
                        error_message = "This Project ID is already used !";
                    } else {
                        boolean result = ps.insertProject(project_id, project_name, project_start, project_end, manager_id, status_id);
                        if (result) {
                            url = "index.jsp";
                        } else {
                            error_message = "Something went wrong, Please Try again";
                        }
                    }
                    if (!error_message.equals("")) {
                        req.setAttribute("PROJECT_INSERT_ERROR", error_message);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("(Project Controller) SQL Execution Error: " + ex.getLocalizedMessage());
                }
                req.getRequestDispatcher(url).forward(req, resp);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        switch (path) {
            case "/project":
                String url = "project-table.jsp";
                List<Project> lst = new ArrayList<>();
                try {
                    boolean res = ps.getAllProjects(lst);
                    if (res) {
                        session.setAttribute("PROJECT_INFO", lst);
                        System.out.println("There are projects found");
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("(Project Controller) SQL Execution Error :" + ex.getLocalizedMessage());
                } finally {
                    req.getRequestDispatcher(url).forward(req, resp);
                }
                break;
            case "/project-add":
                List<User> lstmgr = (List<User>) session.getAttribute("LIST_MANAGER");
                String urlget = "";
                if (lstmgr == null) {
                    lstmgr = new ArrayList<>();
                    try {
                        boolean getManager = us.getAllManager(lstmgr);
                        if (getManager) {
                            urlget = "project-add.jsp";
                            session.setAttribute("LIST_MANAGER", lstmgr);
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        System.out.println("(Project Controller) SQL Execution Error : " + ex.getLocalizedMessage());
                    }
                }
                req.getRequestDispatcher(urlget).forward(req, resp);
                break;
            default:
                break;

        }
    }
}
