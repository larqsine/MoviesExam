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

    private List<Category> categories;

    public Movie(int id, String name, Double rating, String fileLink, Date lastView, Double personalRating, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.personalRating = personalRating;
        intializeCategories(categories);
    }

    private void intializeCategories(List<Category> categories) {
        if (categories == null) {
            this.categories = new ArrayList<>();
        } else {
            this.categories = categories;
        }
    }

    public Movie(String name, Double rating, String fileLink, Date lastView, Double personalRating, List<Category> categories) {
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.personalRating = personalRating;
        intializeCategories(categories);
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
                ", categories=" + categories +
                '}';
    }
}
