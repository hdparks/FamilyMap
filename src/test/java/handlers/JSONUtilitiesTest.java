package handlers;

import domain.Event;
import org.junit.Test;
import requests.PersonRequest;
import responses.EventIDResponse;
import responses.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class JSONUtilitiesTest {

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

        expected = "{\n" +
                "  \"event\": {\n" +
                "    \"eventID\": \"eventId\",\n" +
                "    \"descendant\": \"descendant\",\n" +
                "    \"personID\": \"personID\",\n" +
                "    \"latitude\": \"latitude\",\n" +
                "    \"longitude\": \"longitude\",\n" +
                "    \"country\": \"country\",\n" +
                "    \"city\": \"city\",\n" +
                "    \"eventType\": \"eventType\",\n" +
                "    \"year\": 2000\n" +
                "  },\n" +
                "  \"success\": true\n" +
                "}";

        res = new EventIDResponse(new Event("eventId","descendant","personID","latitude","longitude","country","city","eventType",2000));
        actual = JSONUtilities.generateResponseJSON(res);

        assertEquals(expected,actual);
    }
}