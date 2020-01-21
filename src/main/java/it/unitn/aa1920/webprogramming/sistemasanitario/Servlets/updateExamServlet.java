package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class updateExamServlet extends HttpServlet {

    private ExamDAO examDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            examDAO = daoFactory.getDAO(ExamDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = getServletContext().getContextPath();
        int codice = -1;
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        try {
            codice = Integer.parseInt(request.getParameter("codice"));
            String data = request.getParameter("data");
            String ora = request.getParameter("ora");
            String stringDataOra = data + " " + ora;
            System.out.println(stringDataOra);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date dataOra = formatter.parse(stringDataOra);
            String referto = request.getParameter("referto");
            float ticket = Float.parseFloat(request.getParameter("ticket"));
            String chiudi = request.getParameter("chiudi");

            if (chiudi == null) {
                //lascio l'esame in sospeso
                examDAO.updateExam(codice, dataOra, referto, ticket, false);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage?error=2"));
            } else {
                //chiudo l'esame
                examDAO.updateExam(codice, dataOra, referto, ticket, true);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage?error=1"));
            }
        } catch (ParseException | DAOException e) {
            //e.printStackTrace();
            response.sendRedirect(response.encodeRedirectURL(contextPath + "doctorPage?error=-1&id=" + codice));
        }

    }
}
