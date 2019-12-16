package it.unitn.aa1920.webprogramming.sistemasanitario.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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
        HttpSession session = ((HttpServletRequest)req).getSession();

        if(session.getAttribute("codiceFiscale") != null ) {
            System.out.println("FILTERED: already logged");
            ServletContext sc = req.getServletContext();
            String contextPath = sc.getContextPath();
            if (!contextPath.endsWith("/")) {
                contextPath += "/";
            }
            ((HttpServletResponse)resp).sendRedirect(((HttpServletResponse)resp).encodeRedirectURL(contextPath + "userPage"));
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
