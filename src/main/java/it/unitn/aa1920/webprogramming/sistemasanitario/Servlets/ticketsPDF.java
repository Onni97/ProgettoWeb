package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.ExamBean;
import it.unitn.aa1920.webprogramming.sistemasanitario.DAO.ExamDAO;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Factory.DAOFactory;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

public class ticketsPDF extends HttpServlet {

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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PDDocument doc = new PDDocument()) {
            String codiceFiscale = (String) request.getSession().getAttribute("codiceFiscale");
            List<ExamBean> listaEsami = examDAO.getExamsDoneOfUser(codiceFiscale);

            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream cont = new PDPageContentStream(doc, page)) {
                cont.beginText();

                //per centrare il titolo
                String title = "Ticket Pagati";

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

                //stampo i dati
                cont.setLeading(14.5f);
                int space = 20;
                cont.setFont(font, 14);
                cont.newLineAtOffset(-startX + 30, -3 * space);


                for (ExamBean esame : listaEsami) {
                    cont.showText("- " + esame.getTicket() + "€");
                    cont.newLineAtOffset(60, 0);
                    cont.showText(DateFormat.getInstance().format(esame.getDataOraFissata()));
                    cont.newLineAtOffset(20, -space);

                    String[] lines = WordUtils.wrap("Esame n." + esame.getCodice() + " - " + esame.getTipo(), 65, "\n", true).split("\n");
                    for (String line : lines) {
                        cont.showText(line);
                        cont.newLineAtOffset(0, -space);
                    }

                    cont.newLineAtOffset(-80, -2 * space);
                }

                cont.endText();

                cont.close();

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=Ticket.pdf");
                doc.save(response.getOutputStream());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
