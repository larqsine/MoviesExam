package be;
import javafx.beans.property.SimpleStringProperty;

public class Genre {
    private Integer id;
    private final SimpleStringProperty name;

    public Genre(int id, SimpleStringProperty name) {
        this.id = id;
        this.name = name;
    }

    public Genre(SimpleStringProperty name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
