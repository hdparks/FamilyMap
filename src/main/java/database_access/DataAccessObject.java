package database_access;

import java.io.File;

public class DataAccessObject {
    protected static String dbPath = "jdbc:sqlite:db"+ File.separator + "familymapserver.sqlite";
}
