package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.UserDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;

@MultipartConfig
public class changeProfileImgServlet extends javax.servlet.http.HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Part filePart = req.getPart("newImg");
            String contextPath = getServletContext().getContextPath();

            //prendo il formato del file
            String fileName = getFileName(filePart);
            assert fileName != null;
            String[] fileNameParts = fileName.split("\\.");
            String fileFormat = fileNameParts[fileNameParts.length - 1];

            String path = getServletContext().getInitParameter("profileImgsPath") + "\\";

            //trovo un nome non usato
            File file = new File(path + "1" + "." + fileFormat);
            int increase = 1;
            while (file.exists()) {
                increase++;
                file = new File(path + increase + "." + fileFormat);
            }
            if (!file.exists()) {
                filePart.write(path + increase + "." + fileFormat);
            }

            //aggiorno il db
            HttpSession session = req.getSession();
            String codiceFiscaleUtente = (String) session.getAttribute("codiceFiscale");

            String oldFoto = userDAO.setNewPhoto(codiceFiscaleUtente, increase + "." + fileFormat);


            //elimino il vecchio file se non è il 0.png
            if (!oldFoto.equals("0.png")) {
                File f = new File(path + oldFoto);
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }

            //reindirizzo alla userPage
            if (!contextPath.endsWith("/")) {
                contextPath += "/";
            }
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
