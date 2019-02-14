package database_access;

/**
 * This exception is thrown when the data access process fails.
 */
public class DataAccessException extends Exception {
    /**
     * Invokes the generation of Exception
     * @param m a message field which details what cause the exception
     */
    public DataAccessException(String m){
        super(m);
    }
}
