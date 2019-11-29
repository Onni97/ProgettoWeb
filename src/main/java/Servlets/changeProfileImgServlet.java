package Servlets;

import Beans.userBean;
import Classes.dbUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
public class changeProfileImgServlet extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("newImg");
        String contextPath = getServletContext().getContextPath();

        //prendo il formato del file
        String fileName = getFileName(filePart);
        assert fileName != null;
        String[] fileNameParts = fileName.split("\\.");
        String fileFormat = fileNameParts[1];

        String path = getServletContext().getInitParameter("profileImgsPath") + "\\";

        //trovo un nome non usato
        File file = new File(path + "1" + "." + fileFormat);
        int increase = 1;
        while(file.exists()){
            increase++;
            file = new File(path + increase + "." + fileFormat);
        }
        if(!file.exists()) {
            filePart.write(path + increase + "." + fileFormat);
        }

        //aggiorno il db
        HttpSession session = req.getSession();
        String codiceFiscaleUtente = ((userBean)session.getAttribute("user")).getCodiceFiscale();
        String oldFoto = dbUtils.setNewPhoto(codiceFiscaleUtente, increase + "." + fileFormat);

        //elimino il vecchio file se non è il 0.png
        if ( !oldFoto.equals("0.png")  ) {
            File f = new File(path + oldFoto);
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }

        //aggiorno l'userbean con la foto nuova
        userBean newUser = new userBean();
        dbUtils.getUserData(codiceFiscaleUtente, newUser);
        session.setAttribute("user", newUser);

        //reindirizzo alla userPage
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));
    }



    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
