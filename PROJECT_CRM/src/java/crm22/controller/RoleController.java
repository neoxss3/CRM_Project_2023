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
                List<Role> res = new ArrayList<>();
                try {
                    boolean test = rs.getAllRole(res);
                    if (test) {
                        req.setAttribute("LIST_ROLE", res);
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("SQL Execution Error" + ex.getLocalizedMessage());
                }finally{
                    req.getRequestDispatcher("role-table.jsp").forward(req, resp);
                }
                break;
            case "/role-add":
                req.getRequestDispatcher("role-add.jsp").forward(req, resp);
                break;
            default:
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/role":
                break;
            case "/role-add":
                String name = req.getParameter("role-name");
                String desc = req.getParameter("role-desc");
                String url = "role-add.jsp";
                req.getRequestDispatcher("role-add.jsp").forward(req, resp);
                boolean test;
                try {
                    test = rs.addRole(name, desc);
                    if (test) {
                        req.setAttribute("ROLE_ADD_STATUS", "SUCCESS");
                        url = "index.jsp";
                    } else {
                        req.setAttribute("ROLE_ADD_STATUS", "ERROR");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("Add role error" + ex.getLocalizedMessage());
                } finally {
                    req.getRequestDispatcher(url).forward(req, resp);
                }
                break;
            default:
                break;
        }

    }

}
