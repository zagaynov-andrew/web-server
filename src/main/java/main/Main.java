package main;

import accounts.AccountService;
import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import servlets.WebSocketChatServlet;

import java.util.logging.Logger;

public  class Main
{
    public static void main(String[] args) throws Exception {

        DBService dbService = new DBService();
        dbService.printConnectInfo();

        AccountService accountService = new AccountService(dbService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        JettyWebSocketServletContainerInitializer.configure(context, null);
        context.addServlet(new ServletHolder(new SignInServlet(accountService)),        "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)),        "/signup");
        context.addServlet(new ServletHolder(new WebSocketChatServlet(accountService)), "/chat");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }

}