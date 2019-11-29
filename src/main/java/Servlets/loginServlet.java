package Servlets;

import Beans.userBean;
import Beans.listaMediciBean;
import Classes.dbUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class loginServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(true);
        String codiceFiscale = req.getParameter("codiceFiscale");
        String password = req.getParameter("password");
        String userLogin = req.getParameter("user");

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        int status = dbUtils.checkUser(codiceFiscale, password);
        if ( status == -2 ) {
            //non riesco a comunicare con il db
            System.out.println("500, non riesco a comunicare con il db");
            resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login"));
        } else {

            //-2 login ok ma non sei un dottore, -1 login errato, 0 utente, 1 dottore
            int loginOk;

            if ( status == 1 ) {
                //login ok
                if( userLogin == null ) {
                    //cerco di fare il login come medico
                    if( dbUtils.getCodiceMedico(codiceFiscale) != -1 ) {
                        //login ok come medico
                        loginOk = 1;
                    } else {
                        //login ok ma non è un medico
                        loginOk = -2;
                    }
                } else {
                    //login ok come utente
                    loginOk = 0;
                }
            } else {
                //login errato
                loginOk = -1;
            }
            
            
            if ( loginOk == -2 ) {
                //login ok ma non è un dottore
                System.out.println("NON SEI UN DOTTORE");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-2"));
                
            } else if ( loginOk == 1 ) {
                //login ok come medico
                System.out.println("LOGIN OK COME DOTTORE");
                
                userBean user = new userBean();
                dbUtils.getUserData(codiceFiscale, user);
                session.setAttribute("user", user);
                
                userBean doctorbean = new userBean();
                dbUtils.getDoctorData(user.getMedicoDiBase(), doctorbean);
                session.setAttribute("usersDoctor", doctorbean);

                listaMediciBean listaMediciDellaProvincia = new listaMediciBean();
                List<Integer> listaCodiciMediciDellaProvincia = new LinkedList<>();
                dbUtils.getListaMediciPerProfincia(user.getProvincia(), listaCodiciMediciDellaProvincia);
                for ( int codiceMedico : listaCodiciMediciDellaProvincia ) {
                    userBean medico = new userBean();
                    dbUtils.getDoctorData(codiceMedico, medico);
                    listaMediciDellaProvincia.addMedico(medico);
                }
                session.setAttribute("listaMediciDellaProvincia", listaMediciDellaProvincia);
                
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "doctorPage"));
                
            } else if ( loginOk == 0 ) {
                //login ok come utente
                System.out.println("LOGIN OK COME UTENTE");
                
                userBean user = new userBean();
                dbUtils.getUserData(codiceFiscale, user);
                session.setAttribute("user", user);
                
                userBean doctorbean = new userBean();
                dbUtils.getDoctorData(user.getMedicoDiBase(), doctorbean);
                session.setAttribute("usersDoctor", doctorbean);

                listaMediciBean listaMediciDellaProvincia = new listaMediciBean();
                List<Integer> listaCodiciMediciDellaProvincia = new LinkedList<>();
                dbUtils.getListaMediciPerProfincia(user.getProvincia(), listaCodiciMediciDellaProvincia);
                for ( int codiceMedico : listaCodiciMediciDellaProvincia ) {
                    userBean medico = new userBean();
                    dbUtils.getDoctorData(codiceMedico, medico);
                    listaMediciDellaProvincia.addMedico(medico);
                }
                session.setAttribute("listaMediciDellaProvincia", listaMediciDellaProvincia);
                
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));
                
            } else {
                //login errato
                System.out.println("PASSWORD SBAGLIATA");
                resp.sendRedirect(resp.encodeRedirectURL(contextPath + "login?error=-1"));
            
            }
        }
    }
}
