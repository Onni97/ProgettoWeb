package Filters;

import Beans.userBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "doctorFilter")
public class checkIfDoctor implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        userBean user = (userBean)session.getAttribute("user");

        if(user.getIsDoctor()) {
            chain.doFilter(req, resp);
        } else {
            System.out.println("FILTERED: not doctor");
            ServletContext sc = req.getServletContext();
            String contextPath = sc.getContextPath();
            if (!contextPath.endsWith("/")) {
                contextPath += "/";
            }
            ((HttpServletResponse)resp).sendRedirect(((HttpServletResponse)resp).encodeRedirectURL(contextPath));
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
