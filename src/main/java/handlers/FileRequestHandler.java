package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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

            if (exchange.getRequestMethod().toUpperCase().equals("GET")){
                //  Parse path
                String path = exchange.getRequestURI().toString();

                //  empty path defaults to /index.html
                if (path.equals("/")) path = "/index.html";

                logger.info(path);

                //  Try to get the requested file
                File requestedFile = new File("familymapserver/web" + path);

                if (requestedFile.isFile()){
                    //  Good to go!
                    exchange.sendResponseHeaders(200,0);
                    ExchangeUtilities.writeFileToOutputStream(requestedFile,exchange.getResponseBody());

                    logger.info(requestedFile.getPath()+ " delivered");

                } else {
                    //  Throw FileNotFound
                    throw new FileNotFoundException("File not found");
                }


            } else{
                //  Expected "GET" request
                throw new HttpRequestException("Invalid request method");
            }

        } catch (FileNotFoundException ex){
            //  Return a 404 message
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND,0);

            //  Return the 404 page
            ExchangeUtilities.writeFileToOutputStream(
                    new File("familymapserver/web/HTML/404.html"),
                    exchange.getResponseBody());

            logger.severe(ex.getMessage());

        } catch (HttpRequestException ex){
            ExchangeUtilities.handleRequestError(ex,exchange);

            logger.severe(ex.getMessage());

        }

        exchange.close();
    }

}
