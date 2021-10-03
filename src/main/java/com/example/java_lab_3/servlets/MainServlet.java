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

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isAuthorized(request))
                processMainPage(request, response);
            else
                redirectToAuth(request, response);
        } catch (Exception e) {
            processError(response, e);
        }
    }

    private void redirectToAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath() + "/login";
        response.sendRedirect(path);
    }

    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = session.getId();
        return SERVICE_LOCATOR.getAccountService().isUserAuthorized(userId);
    }

    private void processError(HttpServletResponse response, Exception e) {
        try {
            response.getWriter().printf("An error has occurred:\n\t %s\n", e.getLocalizedMessage());
        }
        catch (Exception in) {
            System.out.printf("Outer: \n\t%s\nInternal:\n\t%s\n", e.getLocalizedMessage(), in.getLocalizedMessage());
        }
    }

    private void processMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        setMainPagesAttributes(request);
        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }

    private void setMainPagesAttributes(HttpServletRequest request) {
        String login = SERVICE_LOCATOR.getAccountService().getUserInfo(request.getSession().getId()).getLogin();

        String path = request.getParameter("path");
        boolean isValid = SERVICE_LOCATOR.getValidator().validatePath(path);
        String pathToken = isValid ? "Path = " + path : "Path was not correct";
        List<FileModel> directories = isValid ?
                SERVICE_LOCATOR.getPathReader().getUserFileSystem(path, login)
                : new ArrayList<>();
        String dateToken = SERVICE_LOCATOR.getDateProvider().getCurrentDate().toString();

        request.setAttribute("name", "app name");
        request.setAttribute("date_now", dateToken);
        request.setAttribute("path", path);
        request.setAttribute("pathToken", pathToken);
        request.setAttribute("dirs", directories);
    }
}