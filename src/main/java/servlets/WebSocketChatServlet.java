package servlets;

import chat.ChatService;
import chat.ChatWebSocket;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import templater.PageGenerator;

import java.io.IOException;
import java.time.Duration;


@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {

    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;

    public WebSocketChatServlet() {
        this.chatService = new ChatService();
    }

    @Override
    public void configure(JettyWebSocketServletFactory factory) {

        factory.setIdleTimeout(Duration.ofMillis(LOGOUT_TIME));
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) {

        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().println(PageGenerator.instance().getPage("chat.html", null));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
