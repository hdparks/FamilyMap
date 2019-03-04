package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.logging.Logger;

public class JSONHandler {

    private static Logger logger = Logger.getLogger("JSONHandler");


    /**
     * Creates a requests instance given an input stream and a target class
     * @param inputStream the given data stream
     * @param tClass the target class type
     * @param <T> the return type
     * @return an instance of the requests class T. Returns null if fails.
     */
    public static <T> T createRequest(InputStream inputStream,Class<T> tClass) throws IOException {

        try(Reader reader = new InputStreamReader(inputStream)) {
            return new Gson().fromJson(reader, tClass);
        }
    }

    public static <T> String generateResponseJSON(T obj){
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }



}
