package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import responses.Response;

import java.io.*;
import java.util.logging.Logger;

public class JSONUtilities {

    private static Logger logger = Logger.getLogger("JSONUtilities");


    /**
     * Creates a requests instance given an input stream and a target class
     * @param inputStream the given data stream
     * @param tClass the target class type
     * @param <T> the return type
     * @return an instance of the requests class T. Returns null if fails.
     */
    public static <T> T createRequestInstance(InputStream inputStream, Class<T> tClass) {

        Reader reader = new InputStreamReader(inputStream);
        Gson gson = new Gson();
        T tObj = gson.fromJson(reader,tClass);
        return tObj;


    }

    public static <T> String generateResponseJSON(T obj){
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }

}
