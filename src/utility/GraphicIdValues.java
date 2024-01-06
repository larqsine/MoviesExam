package utility;

public enum GraphicIdValues {
    SEARCH("search"), UNDO("undo"), UP("upButton"), DOWN("downButton");

    private final String value;

    GraphicIdValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
