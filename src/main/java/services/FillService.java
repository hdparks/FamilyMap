package services;

/**
 * Populates the server's database with generated data for the specified username.
 *
 * The required 'username' parameter must be a registered user.
 * If there is any data in the database already associated with the given username, it is deleted.
 * The optional 'generations' parameter lets the caller specify the number of generations of ancestors to be generated,
 * and must be a non-negative integer, {default = 4}.
 */
public class FillService {

    /**
     * Parse the (username, generations) input,
     * @param username
     * @param generations
     * @return
     */
    public FillResponse fill(String username, int generations){
        return null;
    }
}
