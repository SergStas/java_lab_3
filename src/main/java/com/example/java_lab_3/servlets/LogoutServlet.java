package com.example.java_lab_3.servlets;

import com.example.java_lab_3.services.accounts.IAccountService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IAccountService accountService = MainServlet.SERVICE_LOCATOR.getAccountService();

        accountService.logOutUser(request.getSession().getId());

        String path = "http://localhost:8080/java_lab_3_war_exploded/login";
        response.sendRedirect(path);
    }
}
