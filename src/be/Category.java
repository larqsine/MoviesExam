package be;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<Movie> movies;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name, List<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }


    public Category(int id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return id + name;
    }
}
