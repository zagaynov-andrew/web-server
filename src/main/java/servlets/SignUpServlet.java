package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


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

        if (login == null || password == null || email == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (accountService.getUserByLogin(login) != null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }

        UserProfile profile = new UserProfile(login, password, email);
        accountService.addNewUser(profile);

        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().println(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
