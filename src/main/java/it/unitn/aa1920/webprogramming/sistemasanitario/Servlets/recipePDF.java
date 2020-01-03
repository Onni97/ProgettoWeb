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

public class recipePDF extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream cont = new PDPageContentStream(doc, page)) {
                //RecipeBean recipe = (RecipeBean)req.getParameter("recipe");
                cont.beginText();

                //per centrare il titolo
                String title = "Ricetta";
                PDFont font = PDType1Font.HELVETICA_BOLD;
                int marginTop = 30;
                int fontSize = 16;

                float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
                float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

                PDRectangle mediaBox = page.getMediaBox();
                float startX = (mediaBox.getWidth() - titleWidth) / 2;
                float startY = mediaBox.getHeight() - marginTop - titleHeight;

                cont.setFont(font, fontSize);
                cont.newLineAtOffset(startX, startY);
                cont.showText(title);

                //stampo la
                cont.newLine();

                cont.endText();

                cont.close();

                resp.setContentType("application/pdf");
                resp.setHeader("Content-Disposition", "inline; filename=Ricetta.pdf");
                doc.save(resp.getOutputStream());
            }
        }
    }
}
