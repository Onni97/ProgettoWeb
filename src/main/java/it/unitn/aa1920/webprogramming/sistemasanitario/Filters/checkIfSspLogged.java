package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class checkIfSspLogged implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        if(session.getAttribute("ssp") == null ) {
            Cookie[] cookies = ((HttpServletRequest) req).getCookies();
            boolean trovato = false;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();

                    if(name.equals("ssp")) {
                        session.setAttribute("ssp", value);
                        chain.doFilter(req, resp);
                        trovato = true;
                    }
                }
            }


            if (! trovato) {
                System.out.println("FILTERED: not logged");
                ServletContext sc = req.getServletContext();
                String contextPath = sc.getContextPath();
                if (!contextPath.endsWith("/")) {
                    contextPath += "/";
                }
                ((HttpServletResponse)resp).sendRedirect(((HttpServletResponse)resp).encodeRedirectURL(contextPath + "login"));
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
