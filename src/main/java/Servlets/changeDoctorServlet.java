package Servlets;

import Beans.listaMediciBean;
import Beans.userBean;
import Classes.dbUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class changeDoctorServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        int newCodiceMedico = Integer.parseInt(req.getParameter("newDoctor").split(" ")[0]);
        String codiceFiscaleUtente = ((userBean)session.getAttribute("user")).getCodiceFiscale();

        dbUtils.setMedicoDiBase(codiceFiscaleUtente, newCodiceMedico);

        userBean user = new userBean();
        dbUtils.getUserData(codiceFiscaleUtente, user);
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

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        resp.sendRedirect(resp.encodeRedirectURL(contextPath + "userPage"));
    }
}
