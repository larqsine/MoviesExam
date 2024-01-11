package utility;

public enum MovieGenre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science Fiction"),
    DOCUMENTARY("Documentary"),
    THRILLER("Thriller"),
    ANIMATION("Animation"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    CRIME("Crime"),
    BIOGRAPHY("Biography"),
    HISTORICAL("Historical"),
    MUSICAL("Musical"),
    WESTERN("Western");

    private final String displayName;

    MovieGenre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
