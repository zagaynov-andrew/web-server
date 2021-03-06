package servlets;

import accounts.AccountService;
import dbService.dataSets.UsersDataSet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * The servlet responsible for registration.
 */
public class SignUpServlet extends HttpServlet {

    private final AccountService accountService;

    /**
     * Constructor.
     * @param accountService An object for storing account information.
     */
    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Signs up a user.
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client.
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSet usersDataSet = null;
        try {
            usersDataSet = accountService.getUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usersDataSet != null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }

        UsersDataSet userProfile = new UsersDataSet(login, email, password);
        try {
            accountService.addNewUser(userProfile);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().println(e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.setContentType("text/html;charset=utf-8");
        accountService.addSession(request.getSession().getId(), userProfile);
        try {
            response.sendRedirect("/sign-in.html");
        } catch (IOException ex) {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
