/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Note;
import models.User;
import services.AccountService;
import services.NoteService;
import servlets.NoteServlet;

/**
 *
 * @author Khanh Nguyen
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String email = (String) session.getAttribute("email");

        UserDB userDB = new UserDB();

        try {
            User user = userDB.get(email);
            if (user.getRole().getRoleId() != 1) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect("notes");
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }
    
    @Override
    public void init(FilterConfig filterConfig) {

    }

}
