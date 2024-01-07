package utility;

public enum PlayButtonGraphic {
    PLAY(">"), STOP("||");
    private final String value;

    PlayButtonGraphic(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
