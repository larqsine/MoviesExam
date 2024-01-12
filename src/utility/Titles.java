package utility;

public enum Titles implements Messages {
    ADD_NEW_SONG("Add new Song"),
    ADD_CATEGORY("Add Category"),
    DELETE_MOVIE("Delete Song"),
    CREATE_PLAYLIST("Create playlist"),
    EDIT_CATEGORY("Edit Category"),
    DELETE_CATEGORY("Delete Category"),;


    private final String value;

    Titles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
