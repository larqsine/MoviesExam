package utility;

public enum InformationalMessages implements Messages {
    FILTER_EMPTY("Filter is empty"),
    NO_INTERNET_CONNECTION("Check your internet connection"),
    FXML_MISSING("Application error, file system corrupt, FXML resource is missing"),
    NO_EMPTY_INPUT("Title and Location can not be empty"),
    NO_SONG_SELECTED("No song selected"),
    OPERATION_FAILED("Operation failed"),
    NO_CATEGORY_SELECTED("No category has been selected"),
    PLAYLIST_IN_USE("The playlist that you are trying to delete is in use"),
    NO_SONG_AND_PLAYLIST("Please select a song and a playlist"),
    DELETE_SUCCEEDED("Deleted with success"),
    DELETE_QUESTION("Are you sure that you want to delete this song"),
    NO_FILE("No file, returned by your path!\nPlease check again"),
    ADD_QUESTION("Are you sure that you want to add "),

    DELETE_MOVIE_QUESTION("Are you sure that you want to delete this movie " + "\n"),

    DELETE_CATEGORY_QUESTION("Are you sure that you want to delete this playlist " + "\n"),

    NO_EMPTY_TITLE("Title can not be empty!"),
    NO_DUPLICATE("Please chose another file, this song is already in the list"),
    DELETE_SONG_PLAYLIST("Are you sure that you want to delete this song from the playlist " + "\n"),
    NO_EMPTY_PLAYLIST("List can not be empty"),
    ORDERING_NOT_PERSISTED("Changes will not be saved into the database"),
    FAIL_MESSAGE_INSTRUCTIONS(" Please check your internet connection or if the files on your computer are not corrupt!"),
    INITIALIZING_ERROR("Application initialization error");


    private final String value;

    InformationalMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}

