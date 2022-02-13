package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The servlet responsible for logging in.
 */
public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    /**
     * Constructor.
     * @param accountService An object for storing account information.
     */
    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    // get logged user profile

    /**
     * Gets logged user profile.
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client.
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) {

        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);

        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
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

    //sign in

    /**
     * Signs in a user.
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client.
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().println("Unauthorized");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.addSession(request.getSession().getId(), profile);
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().println("Authorized: " + login);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Signs out a user.
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client.
     */
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) {

        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);

        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().println("Goodbye!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
