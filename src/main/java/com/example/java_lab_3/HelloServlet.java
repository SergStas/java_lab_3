package com.example.java_lab_3;

import com.example.java_lab_3.models.FileModel;
import com.example.java_lab_3.services.dateprovider.TimeService;
import com.example.java_lab_3.services.factory.Factory;
import com.example.java_lab_3.services.factory.IFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/")
public class HelloServlet extends HttpServlet {
    private final static IFactory _services = new Factory();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String path = request.getParameter("path");
        boolean isValid = _services.getValidator().validatePath(path);
        String pathToken = isValid ? "Path = " + path : "Path was not correct";
        List<FileModel> directories = isValid ? _services.getPathReader().getListOfDirs(path) : new ArrayList<>();

        String dateToken = _services.getDateProvider().getCurrentDate().toString();

        request.setAttribute("name", "app name");
        request.setAttribute("date_now", dateToken);
        request.setAttribute("path", path);
        request.setAttribute("pathToken", pathToken);
        request.setAttribute("dirs", directories);

        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }
}