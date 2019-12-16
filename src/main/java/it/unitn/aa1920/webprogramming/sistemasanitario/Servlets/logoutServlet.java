package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class logoutServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("LOGOUT");
        HttpSession session = req.getSession(true);
        session.invalidate();

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login"));
    }
}