package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class checkIfAlreadyLogged implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        ServletContext sc = req.getServletContext();
        String contextPath = sc.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        if (session.getAttribute("codiceFiscale") != null) {
            System.out.println("FILTERED: already logged");
            ((HttpServletResponse) resp).sendRedirect(((HttpServletResponse) resp).encodeRedirectURL(contextPath + "userPage"));
        } else if (session.getAttribute("ssp") != null) {
            System.out.println("FILTERED: already logged");
            ((HttpServletResponse) resp).sendRedirect(((HttpServletResponse) resp).encodeRedirectURL(contextPath + "sspPage"));
        } else {
            Cookie[] cookies = ((HttpServletRequest) req).getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();

                    if(name.equals("user")) {
                        session.setAttribute("codiceFiscale", value);
                        ((HttpServletResponse) resp).sendRedirect(((HttpServletResponse) resp).encodeRedirectURL(contextPath + "userPage"));
                    } else if(name.equals("ssp")) {
                        session.setAttribute("ssp", value);
                        ((HttpServletResponse) resp).sendRedirect(((HttpServletResponse) resp).encodeRedirectURL(contextPath + "sspPage"));
                    }
                }
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
