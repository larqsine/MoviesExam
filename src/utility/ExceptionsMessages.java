package utility;

public enum ExceptionsMessages implements Messages {
    READING_FROMDB_FAILED("Reading  from the data base gone wrong." + "\n" + InformationalMessages.NO_INTERNET_CONNECTION.getValue()),
    READING_MOVIE_LOCAL("Reading file from local storage went wrong"),

    DATE_UPDATE_FAILED("Database error when updating the date"),
    NO_DATABASE_CONNECTION("Error connecting to the database"),
    TRANSACTION_FAILED("Error saving changes to the database"),
    DB_UNSUCCESFULL("Database operation unsuccessful");
    private final String value;

    ExceptionsMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
