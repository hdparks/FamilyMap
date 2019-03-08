import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class FamilyMapServer {

    private static Logger logger = Logger.getLogger("FamilyMapServer");


    public static void main(String[] args){
        //  Require port number
        if (args.length < 1){
            System.out.println("USAGE: java /path/to/FamilyMapServer portNumber\n" +
                    "where portNumber is an integer in [1-65535]");
            return;
        }

        //  Validate port number
        int port = Integer.parseInt(args[0]);

        if (port > 65535 || port < 1){
            System.out.println("USAGE: java /path/to/FamilyMapServer portNumber\n" +
                    "where portNumber is an integer in [1-65535]");
            return;
        }


        //  Start the server
        FamilyMapServer server = new FamilyMapServer();
        try {
            server.startServer(port);
        } catch (IOException ex){
            logger.severe(ex.getMessage());
            logger.severe("Server could not be started, please try again");
        }
    }




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
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());

    }
}
