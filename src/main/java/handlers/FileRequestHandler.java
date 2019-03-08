package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import handlers.HttpExceptions.HttpBadRequestException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class FileRequestHandler implements HttpHandler {

    public static Logger logger = Logger.getLogger("FileRequestHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            if (!exchange.getRequestMethod().toUpperCase().equals("GET")) {
                throw new HttpBadRequestException("Invalid request method");
            }

            String path = exchange.getRequestURI().toString();
            if (path.equals("/")) path = "/index.html";


            File requestedFile = new File("familymapserver/web" + path);
            if (requestedFile.isFile()){

                //  Good to go!
                exchange.sendResponseHeaders(200,0);
                ExchangeUtilities.writeFileToOutputStream(requestedFile,exchange.getResponseBody());

            } else {
                //  Throw FileNotFound
                throw new FileNotFoundException("File not found");
            }


        } catch (FileNotFoundException ex){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND,0);
            ExchangeUtilities.writeFileToOutputStream(
                    new File("familymapserver/web/HTML/404.html"),
                    exchange.getResponseBody());


        } catch (HttpBadRequestException ex){
            exchange.sendResponseHeaders(400,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } catch (Exception ex){
            ex.printStackTrace();
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } finally {

            exchange.close();
        }

    }

}
