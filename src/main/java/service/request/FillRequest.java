package service.request;

/**
 * Represents a request made to the /fill API endpoint
 */
public class FillRequest implements Request {
    /**
     * The username of the User whose generations are to be filled
     */
    String userName;

    /**
     * The number of generations to be filled
     */
    int generations;

}
