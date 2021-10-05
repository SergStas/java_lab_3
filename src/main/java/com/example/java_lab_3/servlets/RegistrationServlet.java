package com.example.java_lab_3.servlets;

import com.example.java_lab_3.models.UserProfile;
import com.example.java_lab_3.services.accounts.IAccountService;
import com.example.java_lab_3.services.accounts.enums.RegistrationResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IAccountService accountService = MainServlet.SERVICE_LOCATOR.getAccountService();

        UserProfile user = extractUserProfile(request);
        RegistrationResult result = accountService.registerUser(user);
        if (result == RegistrationResult.OK) {
            accountService.logInUser(user, request.getSession());
            redirectToMainPage(request, response);
        }
        else
            backWithError(request, response, getErrorMessage(result));
    }

    private String getErrorMessage(RegistrationResult res) {
        switch (res) {
            case LOGIN_IS_OCCUPIED: return "Login is already occupied";
            case EMAIL_IS_OCCUPIED: return "Email is already registered";
            case INVALID_EMAIL: return "Invalid email: email must be in format: some@email.com";
            case INVALID_PASSWORD: return "Password is too short, min length = 4";
            case INVALID_LOGIN: return "Invalid login: login must be at least 3 symbols and must not contain '@'-sign";
            case FIELDS_ARE_EMPTY: return "Please, make sure you have filled all the fields";
            default: return null;
        }
    }

    private void backWithError(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("error", error);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private UserProfile extractUserProfile(HttpServletRequest request) {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserProfile result = new UserProfile(login, password, email);
        result.setId(login);

        return result;
    }

    private void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath().split("/register")[0] + "?path=/";
        response.sendRedirect(path);
    }
}
