/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm22.filter;

import crm22.entity.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author anhbs
 */
@WebFilter(filterName = "userFilter", urlPatterns = {})
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println("Filter nay da duoc goi");

        String path = req.getServletPath();
        HttpSession session = req.getSession(true);
        String contextPath = req.getContextPath();
        User current = (User) session.getAttribute("USER_INFO");

        switch (path) {
            case "/role-add":
                if (current.getRole().getName().equals("ADMIN")) {
                    chain.doFilter(request, response);
                } else {
                    req.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;
            default:
                req.getRequestDispatcher("404.jsp").forward(request, response);
                break;
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
