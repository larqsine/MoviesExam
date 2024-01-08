package utility;

public enum Titles implements Messages {
    ADD_NEW_SONG("Add new Song"),
    EDIT_SONG("Edit Song"),
    DELETE_SONG("Delete Song"),
    CREATE_PLAYLIST("Create playlist"),
    EDIT_PLAYLIST("Edit playlist"),
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
