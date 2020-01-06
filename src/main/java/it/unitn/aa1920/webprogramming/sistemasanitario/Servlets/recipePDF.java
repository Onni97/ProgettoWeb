package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;

public class recipePDF extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream cont = new PDPageContentStream(doc, page)) {
                RecipeBean recipe = (RecipeBean) req.getAttribute("recipe");
                cont.beginText();

                //per centrare il titolo
                String title = "Ricetta Farmaceutica";

                PDFont font = PDType1Font.HELVETICA_BOLD;
                int marginTop = 30;
                int fontSize = 24;

                float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
                float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

                PDRectangle mediaBox = page.getMediaBox();
                float startX = (mediaBox.getWidth() - titleWidth) / 2;
                float startY = mediaBox.getHeight() - marginTop - titleHeight;

                cont.setFont(font, fontSize);
                cont.newLineAtOffset(startX, startY);
                cont.showText(title);

                //stampo i dati della ricetta
                int space = 20;
                cont.setFont(font, 14);
                cont.newLineAtOffset(-startX + 30, -3*space);
                if (recipe.getVisita().getCodice() == -1) {
                    cont.showText("Codice medico: " + recipe.getEsame().getVisita().getMedicoDiBase().getCodiceMedico());
                    cont.newLineAtOffset(0, -space);
                    cont.showText("Codice Fiscale Paziente: " + recipe.getEsame().getVisita().getUtente().getCodiceFiscale());
                    cont.newLineAtOffset(0, -space);
                } else {
                    cont.showText("Codice Medico: " + recipe.getVisita().getMedicoDiBase().getCodiceMedico());
                    cont.newLineAtOffset(0, -space);
                    cont.showText("Codice Fiscale Paziente: " + recipe.getVisita().getUtente().getCodiceFiscale());
                    cont.newLineAtOffset(0, -space);
                }
                cont.showText("Data e Ora: " + DateFormat.getInstance().format(recipe.getData()));
                cont.newLineAtOffset(0, -space);
                cont.newLineAtOffset(0, -space);
                cont.showText("Farmaco: " + recipe.getFarmaco());
                cont.newLineAtOffset(0, -space);
                cont.showText("Quantità: " + recipe.getQuantita());
                cont.newLineAtOffset(0, -space);
                cont.showText("Descrizione: " + recipe.getDescrizioneFarmaco());
                cont.newLineAtOffset(0, -space);
                cont.newLineAtOffset(0, -space);

                cont.showText("Codice Prescrizione: " + recipe.getCodice());
                cont.newLineAtOffset(0, -space);
                if (recipe.getDataOraEvasa() == null) {
                    cont.showText("NON EVASA");
                } else {
                    cont.showText("EVASA IN DATA: " + DateFormat.getInstance().format(recipe.getDataOraEvasa()));
                }



                cont.endText();

                cont.close();

                resp.setContentType("application/pdf");
                resp.setHeader("Content-Disposition", "inline; filename=Ricetta.pdf");
                doc.save(resp.getOutputStream());
            }
        }
    }
}
