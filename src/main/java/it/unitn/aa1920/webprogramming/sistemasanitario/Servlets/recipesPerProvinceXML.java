package it.unitn.aa1920.webprogramming.sistemasanitario.Servlets;

import io.starter.OpenXLS.WorkBookHandle;
import io.starter.OpenXLS.WorkSheetHandle;
import io.starter.formats.XLS.WorkSheetNotFoundException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Beans.RecipeBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class recipesPerProvinceXML extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkBookHandle workbook = new WorkBookHandle();
        WorkSheetHandle worksheet = workbook.createWorkSheet("Ricette");
        try {
            workbook.getWorkSheet(0).remove();
            workbook.getWorkSheet(0).remove();
            workbook.getWorkSheet(0).remove();
        } catch (WorkSheetNotFoundException e) {
            e.printStackTrace();
        }

        worksheet.add("DATA E ORA", 0, 0);
        worksheet.add("FARMACO", 0, 1);
        worksheet.add("QUANTITA'", 0, 2);
        worksheet.add("MEDICO DI BASE", 0, 3);
        worksheet.add("PAZIENTE", 0, 4);

        //stampo le ricette
        HttpSession session = request.getSession();
        LinkedList<RecipeBean> listaRicette = (LinkedList<RecipeBean>) request.getAttribute("recipeList");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy H:mm");
        int counterRow = 2;
        String currentDataOra = "";
        for (RecipeBean recipe : listaRicette) {
            if (currentDataOra.equals("")) {
                currentDataOra = dateFormat.format(recipe.getData());
            }

            if (!currentDataOra.equals(dateFormat.format(recipe.getData()))) {
                currentDataOra = dateFormat.format(recipe.getData());
                counterRow++;
            }
            worksheet.add(dateFormat.format(recipe.getData()), counterRow, 0);
            worksheet.add(recipe.getFarmaco(), counterRow, 1);
            worksheet.add(recipe.getQuantita(), counterRow, 2);
            if (recipe.getEsame().getCodice() == -1) {
                worksheet.add(recipe.getVisita().getMedicoDiBase().getNome() + " " + recipe.getVisita().getMedicoDiBase().getCognome(), counterRow, 3);
                worksheet.add(recipe.getVisita().getUtente().getNome() + " " + recipe.getVisita().getUtente().getCognome(), counterRow, 4);
            } else {
                worksheet.add(recipe.getEsame().getVisita().getMedicoDiBase().getNome() + " " + recipe.getEsame().getVisita().getMedicoDiBase().getCognome(), counterRow, 3);
                worksheet.add(recipe.getEsame().getVisita().getUtente().getNome() + " " + recipe.getEsame().getVisita().getUtente().getCognome(), counterRow, 4);
            }
            counterRow++;
        }

        response.setContentType("text/xls;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=Ricette.xls");
        OutputStream servletOut = response.getOutputStream();
        workbook.write(servletOut);
        servletOut.flush();
        workbook.close();
    }
}
