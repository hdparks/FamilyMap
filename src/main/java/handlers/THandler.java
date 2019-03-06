package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import services.HttpRequestException;
import services.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Generic Handler object whose functionality matches many Handler footprints
 * Thus, we can write most of the shared logic here and extend a version
 * of THandler with the correct Request and Response types
 * @param <Req> the specific Request Type
 * @param <Res> the specific Response Type
 */
public class THandler<Req,Res> implements HttpHandler {

    private Logger logger;

    final Class<Req> reqClass;
    final Class<Res> resClass;

    Service<Req,Res> service = null;
    String requestMethod;
    boolean authRequired;

    THandler(Class<Req> reqClass, Class<Res> resClass, Logger logger, String method, boolean authRequired){
        this.reqClass = reqClass;
        this.resClass = resClass;
        this.logger = logger;
        this.requestMethod = method;
        this.authRequired = authRequired;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            //  Expect the supplied method requests
            if(exchange.getRequestMethod().toUpperCase().equals(this.requestMethod)) {

                if(!authRequired || AuthUtilities.authTokenIsValid(AuthUtilities.getAuthToken(exchange))){

                    //  Parse request body into RegisterRequest object

                    Req req = JSONUtilities.createRequestInstance(exchange.getRequestBody(), this.reqClass);

                    //  Pass the request to the RegisterService to get the response data
                    Res res = this.service.serveResponse(req);

                    //  Write to the response object
                    ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

                } else {
                    //  Authentication failure
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED,0);
                    throw new HttpRequestException("Authentication failed");
                }

            } else {
                //  We expected a different request method to this endpoint.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                throw new HttpRequestException("Invalid request method");
            }

        } catch (DataAccessException ex ){
            //  Something went wrong on the server side, so we return
            ExchangeUtilities.handleInternalError(ex,exchange);

            logger.log(Level.SEVERE,ex.getMessage());

        } catch (HttpRequestException ex){
            //  Something went wrong with the request
            ExchangeUtilities.handleRequestError(ex,exchange);

            logger.severe(ex.getMessage());
        }
        exchange.close();
    }
}
