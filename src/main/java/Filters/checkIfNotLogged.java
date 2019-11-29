package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "loggedFilter")
public class checkIfNotLogged implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        if(session.getAttribute("user") == null ) {
            System.out.println("FILTERED: not logged");
            ServletContext sc = req.getServletContext();
            String contextPath = sc.getContextPath();
            if (!contextPath.endsWith("/")) {
                contextPath += "/";
            }
            ((HttpServletResponse)resp).sendRedirect(((HttpServletResponse)resp).encodeRedirectURL(contextPath + "login"));
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) {

    }

}
