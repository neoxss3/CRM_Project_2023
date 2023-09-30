/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.controller;

import crm22.entity.Role;
import crm22.entity.User;
import crm22.service.RoleService;
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
@WebServlet(name = "UserController", urlPatterns = {"/user-table", "/user-add", "/user-detail"})
public class UserController extends HttpServlet {

    private static final UserService us = new UserService();
    private static final RoleService rs = new RoleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/user-table":
                req.getRequestDispatcher("user-table.jsp").forward(req, resp);
                break;
            case "/user-add":
                String url = "user-add.jsp";
                String name = req.getParameter("crename");
                String mail = req.getParameter("cremail");
                String pass = req.getParameter("crepass");
                String confirm = req.getParameter("confirmpass");
                String address = req.getParameter("addr");
                String phone = req.getParameter("crephone");
                int role = Integer.parseInt(req.getParameter("crerole"));
                if (!confirm.equals(pass)) {
                    req.setAttribute("CREATE_USER_ERROR", "Password not match");
                } else if (name.length() < 5 || name.length() > 20) {
                    req.setAttribute("CREATE_USER_ERROR", "Your name must have 6 - 20 characters");
                } else {
                    User usr = new User();
                    usr.setFullName(name);
                    usr.setEmail(mail);
                    usr.setPassword(pass);
                    usr.setAddress(address);
                    usr.setPhone(phone);
                    Role rl = new Role();
                    rl.setId(role);
                    usr.setRole(rl);
                    try {
                        boolean res = us.addUser(usr);
                        if (res) {
                            url = "user-table.jsp";
                        } else {
                            req.setAttribute("CREATE_USER_ERROR", "Add User Failed");
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        System.out.println("(User Controller) SQL Execution Error" + ex.getLocalizedMessage());
                    }
                }
                req.getRequestDispatcher(url).forward(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        List<User> res = (List<User>) session.getAttribute("LIST_USER");
        if (res == null) {
            res = new ArrayList<>();
            try {
                boolean test = us.getAllUser(res);
                if (test) {
                    session.setAttribute("LIST_USER", res);
                } else {
                    req.setAttribute("EMPTY_USERS", "No users found");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("(User Controller) SQL Execution Error" + ex.getLocalizedMessage());
            }
        }

        List<Role> lstrl = (List<Role>) session.getAttribute("LIST_ROLE");
        if (lstrl == null) {
            lstrl = new ArrayList<>();
            try {
                boolean test = rs.getAllRole(lstrl);
                if (test) {
                    session.setAttribute("LIST_ROLE", lstrl);
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("(User Controller) SQL Execution Error" + ex.getLocalizedMessage());
            }
        }
        switch (path) {
            case "/user-table":
                req.getRequestDispatcher("user-table.jsp").forward(req, resp);
                break;
            case "/user-add":
                req.getRequestDispatcher("user-add.jsp").forward(req, resp);
                break;
            case "/user-detail":
                String url = "user-detail.jsp";
                int id = Integer.parseInt(req.getParameter("userid"));

                try {
                    User nus = us.getDetailedUser(id);
                    if (nus != null) {
                        req.setAttribute("USER_INFO", nus);
                    } else {
                        url = "404.html";
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("(User Controller) SQL Execution Error: " + ex.getLocalizedMessage());
                }
                req.getRequestDispatcher(url).forward(req, resp);
                break;
            default:
                break;
        }
    }

}
