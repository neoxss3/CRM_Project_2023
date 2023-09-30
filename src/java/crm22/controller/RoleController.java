/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.controller;

import crm22.entity.Role;
import crm22.service.RoleService;
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
@WebServlet(name = "RoleController", urlPatterns = {"/role", "/role-add"})
public class RoleController extends HttpServlet {

    private RoleService rs = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/role":
                HttpSession session = req.getSession();
                List<Role> res = (List<Role>) session.getAttribute("LIST_ROLE");
                if (res == null) {
                    res = new ArrayList<>();
                    try {
                        boolean test = rs.getAllRole(res);
                        if (test) {
                            session.setAttribute("LIST_ROLE", res);
                        }
                    } catch (ClassNotFoundException ex) {
                        System.out.println("(Role Con5troller) SQL Execution Error" + ex.getLocalizedMessage());
                    }
                }
                req.getRequestDispatcher("role-table.jsp").forward(req, resp);
                break;
            case "/role-add":
                req.getRequestDispatcher("role-add.jsp").forward(req, resp);
                break;
            default:
                resp.sendRedirect("404.jsp");
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/role":
                resp.sendRedirect("404.jsp");
                break;
            case "/role-add":
                String url = "role-add.jsp";
                String error_message = "";
                int id = Integer.parseInt(req.getParameter("role_id"));
                String name = req.getParameter("role_name");
                String desc = req.getParameter("role_desc");

                try {
                    boolean checkID = rs.checkDupplicateID(id);
                    boolean checkName = rs.checkDupplicateName(name.toUpperCase());
                    if (checkID) {
                        error_message = "This role ID has been used !";
                    } else {
                        if (checkName) {
                            error_message = "This role name has been used !";
                        } else {
                            boolean test = rs.addRole(id, name, desc);
                            if (test) {
                                url = "index.jsp";
                            } else {
                                error_message = "Something went wrong, please try again !";
                            }
                        }
                    }

                    if (!error_message.equals("")) {
                        req.setAttribute("ROLE_INSERT_ERROR", error_message);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("(Role Controller) SQL Execution Error" + ex.getLocalizedMessage());
                }

                req.getRequestDispatcher(url).forward(req, resp);

                break;
            default:
                resp.sendRedirect("404.jsp");
                break;
        }

    }

}
