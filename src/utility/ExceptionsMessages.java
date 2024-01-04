package utility;

public enum ExceptionsMessages implements Messages {
    READING_FROMDB_FAILED("Reading  from the data base gone wrong." + "\n" + InformationalMessages.NO_INTERNET_CONNECTION.getValue()),
    READING_SONG_LOCAL("Reading file from local storage went wrong"),
    UPDATE_SONG_FAILED("Database error when tried to update the song"),
    DELETE_SONG_FAILED("Error when trying to delete from data base"),
    SONG_CREATION_FAILED("Database error when tried to create the song"),
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
