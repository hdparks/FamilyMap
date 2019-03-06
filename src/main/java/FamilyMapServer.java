import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.FileHandler;

public class FamilyMapServer {



    private void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }

    private void registerHandlers(HttpServer server){
        //  Register all handlers
        //  server.createContext("/path", new PathHandler());

        server.createContext("/", new FileRequestHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load",new LoadHandler());
        server.createContext("/person/[personID]", new PersonIDHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event/[eventID]", new EventIDHandler());
        server.createContext("/event", new EventHandler());

    }
}
