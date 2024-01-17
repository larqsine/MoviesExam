package utility;

public enum InformationalMessages implements Messages {
    FILTER_EMPTY("Filter is empty"),
    NO_INTERNET_CONNECTION("Check your internet connection"),
    FXML_MISSING("Application error, file system corrupt, FXML resource is missing"),
    NO_EMPTY_INPUT("Title and Location can not be empty"),
    NO_CATEGORY_OPENED("No category opened, please open a category"),
    NO_MOVIE_OPENED("No movie opened, please open a movie"),
    OPERATION_FAILED("Operation failed"),
    NO_CATEGORY_SELECTED("No category has been selected"),
    CATEGORY_IN_USE("The category that you are trying to delete is in use"),
    MOVIE_IN_USE("The movie that you want to delete is in use"),
    DELETE_SUCCEEDED("Deleted with success"),
    DELETE_QUESTION("Are you sure that you want to delete this song"),
    NO_FILE("No file, returned by your path!\nPlease check again"),
    DELETE_CATEGORY_QUESTION("Are you sure that you want to delete this playlist " + "\n"),
    NO_EMPTY_TITLE("Title can not be empty!"),
    NO_CATEGORY_CHECKED("Please mark a category"),
    INITIALIZING_ERROR("Application initialization error");


    private final String value;

    InformationalMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}

