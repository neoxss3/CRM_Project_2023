/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.service;

import crm22.entity.User;
import crm22.repositories.UserRepository;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhbs
 */
public class LoginService {

    private UserRepository ur = new UserRepository();

    public boolean checkLogin(HttpServletRequest request, String email, String pass) throws ClassNotFoundException, SQLException {
        User result = ur.checkLogin(email, pass);
        if (result != null) {
            HttpSession session = request.getSession();
            session.setAttribute("USER_INFO", result);
            return true;
        }
        return false;
    }
}
