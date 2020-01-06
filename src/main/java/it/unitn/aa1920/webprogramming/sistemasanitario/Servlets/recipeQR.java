package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;

public class recipeQR extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecipeBean recipe = (RecipeBean) req.getAttribute("recipe");
        String dataQR = "";
        if (recipe.getVisita().getCodice() == -1) {
            dataQR = "{'Codice medico': '" + recipe.getEsame().getVisita().getMedicoDiBase().getCodiceMedico() + "', " +
                    "'Codice fiscale paziente': '" + recipe.getEsame().getVisita().getUtente().getCodiceFiscale() + "', " +
                    "'Data prescrizione': '" + DateFormat.getInstance().format(recipe.getData()) + "', " +
                    "'Codice prescrizione': '" + recipe.getCodice() + "', " +
                    "'Farmaco': '" + recipe.getFarmaco() + "', " +
                    "'Quantità': '" + recipe.getQuantita() + "'";
        } else {
            dataQR = "{'Codice medico': '" + recipe.getVisita().getMedicoDiBase().getCodiceMedico() + "', " +
                    "'Codice fiscale paziente': '" + recipe.getVisita().getUtente().getCodiceFiscale() + "', " +
                    "'Data prescrizione': '" + DateFormat.getInstance().format(recipe.getData()) + "', " +
                    "'Codice prescrizione': '" + recipe.getCodice() + "', " +
                    "'Farmaco': '" + recipe.getFarmaco() + "', " +
                    "'Quantità': '" + recipe.getQuantita() + "'";
        }
        if (recipe.getDataOraEvasa() == null) {
            dataQR += ", 'evasa': false}";
        } else {
            dataQR += ", 'evasa': true}";
        }

        ByteArrayOutputStream out = QRCode.from(dataQR).to(ImageType.PNG).withSize(250, 250).stream();

        resp.setContentType("image/png");
        resp.setContentLength(out.size());

        OutputStream outStream = resp.getOutputStream();

        outStream.write(out.toByteArray());

        outStream.flush();
        outStream.close();
    }
}
