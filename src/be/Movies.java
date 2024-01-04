package be;

import java.util.Date;

public class Movies {
    private int id;
    private String name;
    private Double rating;
    private String fileLink;
    private Date lastView;

    public Movies(int id, String name, Double rating, String filelink, Date lastView) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastView = lastView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public Date getLastView() {
        return lastView;
    }

    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    public Movies(String name, Double rating, String filelink, Date lastView) {
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastView = lastView;
    }
}
