package com.example.java_lab_3.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download/")
public class DownloadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getParameter("path");

        String root = "D:\\git\\OOA\\java_lab_3";
        path = root + path.replace("/", "\\");

        File file = new File(path);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=" + file.getName());

        FileInputStream fileIn = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[4096];
        int length = -1;

        while((length = fileIn.read(outputByte)) > 0)
            out.write(outputByte, 0, length);

        fileIn.close();
        out.flush();
        out.close();
    }
}
