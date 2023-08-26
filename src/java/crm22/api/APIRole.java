/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.api;

import com.google.gson.Gson;
import crm22.entity.Role;
import crm22.payload.response.BaseResponse;
import crm22.service.RoleService;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "APIRole", urlPatterns = {"/api/role/delete"})
public class APIRole extends HttpServlet {

    private Gson js = new Gson();
    private RoleService rs = new RoleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("role_id"));
        try {
            boolean result = rs.deleteRole(id);
            BaseResponse resp = new BaseResponse();
            resp.setStatusCode(200);
            resp.setMessage((result) ? "Xóa thành công" : "Xóa thất bại");
            resp.setData(result);
            
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gs = new Gson();
            
            out.print(gs.toJson(resp));
            out.flush();
        } catch (ClassNotFoundException ex) {
            System.out.println("Delete Error" + ex.getLocalizedMessage());
        }

        /*
        Role test = new Role(6, "abc", "xyz");
        String datajs = js.toJson(test);
        String jsdata = "{\"id\":6,\"name\":\"abc\",\"desc\":\"xyz\"}";
        Role back = js.fromJson(jsdata, Role.class);
         */
    }

}
