package Servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

public class profileImage extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedFile = req.getPathInfo();
        String path = getServletContext().getInitParameter("profileImgsPath");
        //File file = new File(path, URLDecoder.decode(requestedFile, "UTF-8"));

        resp.setContentType("image/jpeg");
        ServletOutputStream out = resp.getOutputStream();
        FileInputStream fin = new FileInputStream(path + "\\" + requestedFile);

        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch =0; ;
        while((ch=bin.read())!=-1)
        {
            bout.write(ch);
        }

        bin.close();
        fin.close();
        bout.close();
        out.close();
    }
}
