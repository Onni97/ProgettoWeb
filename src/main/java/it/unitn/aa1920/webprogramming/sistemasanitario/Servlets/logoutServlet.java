package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import javax.servlet.http.Cookie;
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

        Cookie codiceFiscaleCoockie = new Cookie("user", null);
        codiceFiscaleCoockie.setMaxAge(0);
        resp.addCookie(codiceFiscaleCoockie);

        Cookie sspCookie = new Cookie("ssp", null);
        sspCookie.setMaxAge(0);
        resp.addCookie(sspCookie);

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login"));
    }
}