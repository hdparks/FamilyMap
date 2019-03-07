package requests;

/**
 * Represents a requests made to the /fill API endpoint
 */
public class FillRequest implements IRequest {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * The username of the User whose generations are to be filled
     */
    private String path;

    /**
     * Create a FillRequest
     * @param path the userName to fill
     */
    public FillRequest(String path){
        this.path = path;
    }

}
