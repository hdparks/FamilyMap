package handlers;

import com.google.gson.Gson;

import domain.Event;
import org.junit.Test;

import domain.Person;
import requests.PersonRequest;
import responses.EventIDResponse;
import responses.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class JSONUtilitiesTest {
    @Test
    public void testGSONtest() throws Exception {
        String info =
                "{\n"
                        +" \"firstName\": \"Sheila\","
                        +"\"lastName\": \"Parker\","
                        +"\"gender\": \"f\","
                        +"                \"personID\": \"Sheila_Parker\","
                        +"                \"father\": \"Patrick_Spencer\","
                        +"\"mother\": \"Im_really_good_at_names\","
                        +"\"descendant\": \"sheila\""
                        +"}";

        Gson gson = new Gson();
        Person person = gson.fromJson(info,Person.class);
        System.out.println(person.toString());
        assert(true);
    }

    @Test
    public void createRequestInstance() throws IOException {
        //  Test: creating a PersonRequest from an input string.
        PersonRequest expected = new PersonRequest("authToken");

        PersonRequest actual = JSONUtilities.createRequestInstance(
                new ByteArrayInputStream("{ 'authToken': 'authToken' }".getBytes()),
                PersonRequest.class );

        assertEquals(expected,actual);

    }

    @Test
    public void generateResponseJSON() {
        //  Test on Response object
        String expected = "{\n" +
                "  \"success\": true\n" +
                "}";

        Response res = new Response(true);
        String actual = JSONUtilities.generateResponseJSON(res);

        assertEquals(actual, expected);

        //  Test on EventIDResponse cast as Response object,
        //  with nested Event field

        expected = "{\n"+
            "  \"descendant\": \"descendant\",\n"
                    +"  \"eventID\": \"eventId\",\n"
                    +"  \"personID\": \"personID\",\n"
                    +"  \"latitude\": \"latitude\",\n"
                    +"  \"longitude\": \"longitude\",\n"
                    +"  \"country\": \"country\",\n"
                    +"  \"city\": \"city\",\n"
                    +"  \"year\": 2000,\n"
                    +"  \"success\": true\n"+
        "}";

        res = new EventIDResponse(new Event("eventId","descendant","personID","latitude","longitude","country","city","eventType",2000));
        actual = JSONUtilities.generateResponseJSON(res);

        assertEquals(expected,actual);


    }

}