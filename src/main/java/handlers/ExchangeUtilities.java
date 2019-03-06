package handlers;

import com.sun.net.httpserver.HttpExchange;
import responses.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ExchangeUtilities {

    public static <T> void writeResponseToHttpExchange(T res, HttpExchange exchange) throws IOException {
        //  JSONify the response object
        String jsonRes = JSONUtilities.generateResponseJSON(res);

        //  Write the JSON to the response body
        OutputStream responseBody = exchange.getResponseBody();
        writeString(jsonRes, responseBody);
    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    public static void handleRequestError(Exception ex, HttpExchange exchange) throws IOException{
        //  Anything wrong with the request bubbles here
        Response res = new Response(ex.getMessage(),false);
        ExchangeUtilities.writeResponseToHttpExchange(res,exchange);
    }

    /**
     * Handles an internal error ex, formatting the response object correctly
     * @param ex the exception
     * @param exchange the HttpExchange object to be acted upon.
     */
    public static void handleInternalError(Exception ex, HttpExchange exchange) throws IOException {
        //  Anything wrong in the actual operation bubbles here.
        //  Send an "Internal Error" header
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);

        Response res = new Response(ex.getMessage(),false);
        ExchangeUtilities.writeResponseToHttpExchange(res,exchange);
    }


}
