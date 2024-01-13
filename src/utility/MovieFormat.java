package utility;

public enum MovieFormat {
    MP4("mp4"),MPEG4("Mpeg4");
private final String value;
    MovieFormat(String value) {
        this.value=value;
    }
    public String getValue() {
        return value;
    }
}
