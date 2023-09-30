/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.api;

import com.google.gson.Gson;
import crm22.entity.User;
import crm22.payload.response.BaseResponse;
import crm22.service.RoleService;
import crm22.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "APIUser", urlPatterns = {"/api/user/delete", "/api/user/update"})
public class APIUser extends HttpServlet {

    private static final Gson gs = new Gson();
    private static final UserService us = new UserService();
    private static final RoleService rs = new RoleService();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/api/user/update":
                //userid=${userId}&fullname=${fullName}&email=${email}&password=${password}&address=${address}&phone=${phone}&roleid=${roleId}
                int id = Integer.parseInt(req.getParameter("userid"));
                String name = req.getParameter("fullname");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String address = req.getParameter("address");
                String phone = req.getParameter("phone");
                String role_name = req.getParameter("roleid");

                try {
                    int roleId = rs.getIDonName(role_name);
                    boolean result = false;
                    if (roleId > 0) {
                        result = us.updateUser(id, name, email, password, address, phone, roleId);
                    }

                    BaseResponse response = new BaseResponse();
                    response.setStatusCode(200);
                    response.setMessage((result) ? "Update thành công" : "Update thất bại");
                    response.setData(result);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    Gson gs = new Gson();

                    out.print(gs.toJson(resp));
                    out.flush();
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println("Update Failed");
                }

                break;
            default:
                break;
        }
    }

}
