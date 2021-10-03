package com.example.java_lab_3.servlets;

import com.example.java_lab_3.models.FileModel;
import com.example.java_lab_3.services.servicelocator.ServiceLocatorImpl;
import com.example.java_lab_3.services.servicelocator.IServiceLocator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    public final static IServiceLocator SERVICE_LOCATOR = new ServiceLocatorImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String path = request.getParameter("path");
        boolean isValid = SERVICE_LOCATOR.getValidator().validatePath(path);
        String pathToken = isValid ? "Path = " + path : "Path was not correct";
        List<FileModel> directories = isValid ? SERVICE_LOCATOR.getPathReader().getListOfDirs(path) : new ArrayList<>();

        String dateToken = SERVICE_LOCATOR.getDateProvider().getCurrentDate().toString();

        request.setAttribute("name", "app name");
        request.setAttribute("date_now", dateToken);
        request.setAttribute("path", path);
        request.setAttribute("pathToken", pathToken);
        request.setAttribute("dirs", directories);

        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }
}