package utility;

public enum Titles implements Messages {
    ADD_NEW_SONG("Add new Song"),
    ADD_CATEGORY("Add Category"),
    DELETE_SONG("Delete Song"),
    CREATE_PLAYLIST("Create playlist"),
    EDIT_CATEGORY("Edit Category"),
    DELETE_PLAYLIST("Delete playlist"),
    ADD_SONG_PLAYLIST("Add song to playlist"),
    DELETE_SONG_PLAYLIST("Delete song from playlist");


    private final String value;

    Titles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
