package com.example.java_lab_3.servlets;

import com.example.java_lab_3.models.UserProfile;
import com.example.java_lab_3.services.accounts.IAccountService;
import com.example.java_lab_3.services.accounts.enums.LoginResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IAccountService accountService = MainServlet.SERVICE_LOCATOR.getAccountService();

        UserProfile user = extractUserProfile(request);
        LoginResult result = accountService.logInUser(user, request.getSession());

        if (result == LoginResult.OK)
            redirectToMainPage(request, response);
        else
            backWithError(request, response, getErrorMessage(result));
    }

    private void backWithError(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("error", error);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private String getErrorMessage(LoginResult res) {
        switch (res) {
            case PROFILE_NOT_FOUND: return "No user found with specified username";
            case INCORRECT_PASSWORD: return "Invalid password";
            default: return null;
        }
    }

    private UserProfile extractUserProfile(HttpServletRequest request) {
        String login = null;
        String email = null;
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        if (username.contains("@"))
            email = username;
        else
            login = username;

        UserProfile result = new UserProfile(login, password, email);
        result.setId(request.getSession().getId());
        return result;
    }

    private void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath().split("/login")[0] + "?path=/";
        response.sendRedirect(path);
    }
}
