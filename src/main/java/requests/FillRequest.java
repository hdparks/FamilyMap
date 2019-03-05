package requests;

/**
 * Represents a requests made to the /fill API endpoint
 */
public class FillRequest implements IRequest {
    /**
     * The username of the User whose generations are to be filled
     */
    String userName;

    /**
     * The number of generations to be filled
     */
    int generations;

    /**
     * Create a FillRequest
     * @param userName the userName to fill
     * @param generations the number of generations to be filled
     */
    public FillRequest(String userName, int generations){
        this.userName = userName;
        this.generations = generations;
    }

}
