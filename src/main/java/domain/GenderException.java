package domain;

import database_access.DataAccessException;

public class GenderException extends DataAccessException {
    GenderException(){
        super("Gender field must be 'm' or 'f'");
    }
}
