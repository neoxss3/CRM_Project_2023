/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.controller;

import crm22.service.LoginService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anhbs
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private final LoginService ls = new LoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "login.jsp";
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        try {
            boolean res = ls.checkLogin(request,email, pass);
            if(res){
                url = "index.jsp";
            }else{
                request.setAttribute("LOGIN_ERROR", "Login Failed, Please check your email and password.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Login Failed" + ex.getLocalizedMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

}
