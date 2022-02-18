package servlets;

import accounts.AccountService;
import chat.ChatService;
import chat.ChatWebSocket;
import dbService.dataSets.UsersDataSet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import templater.PageGenerator;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {

    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;
    private final AccountService accountService;

    public WebSocketChatServlet(AccountService accountService) {

        this.chatService = new ChatService();
        this.accountService = accountService;
    }

    @Override
    public void configure(JettyWebSocketServletFactory factory) {

        factory.setIdleTimeout(Duration.ofMillis(LOGOUT_TIME));
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        UsersDataSet userProfile = accountService.getUserBySessionId(request.getSession().getId());

        if (userProfile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.sendRedirect("/sign-in.html");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        pageVariables.put("login", userProfile.getLogin());
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().println(PageGenerator.instance().getPage("chat.html", pageVariables));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
