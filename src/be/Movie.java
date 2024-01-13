package be;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private Double rating;
    private String fileLink;
    private Date lastView;
    private Double personalRating;

    public void setPersonalRating(Double personalRating) {
        this.personalRating = personalRating;
    }

    public Double getPersonalRating() {
        return personalRating;
    }

    private List<Genre> genres;

    public Movie(int id, String name, Double rating, String fileLink, Date lastView, Double personalRating, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.personalRating = personalRating;
        intializeGenres(genres);
    }

    private void intializeGenres(List<Genre> genres) {
        if (genres == null) {
            this.genres = new ArrayList<>();
        } else {
            this.genres = genres;
        }
    }

    public Movie(String name, Double rating, String fileLink, Date lastView, Double personalRating, List<Genre> genres) {
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.personalRating = personalRating;
        intializeGenres(genres);
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public Date getLastView() {
        return lastView;
    }

    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setCategories(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", fileLink='" + fileLink + '\'' +
                ", lastView=" + lastView +
                ", personalRating=" + personalRating +
                ", categories=" + genres +
                '}';
    }
}
