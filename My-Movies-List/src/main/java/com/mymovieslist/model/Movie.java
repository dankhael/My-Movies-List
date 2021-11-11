package com.mymovieslist.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = true)
    private Long id;
    private String titleId;
    private String name;
    private int launchDate;
    private Double rating;
    private String imageUrl;

    private Boolean favorite;

    public Movie() {}

    public Movie(String titleId, String name, int launchDate, Double rating, String imageUrl) {
        this.titleId = titleId;
        this.name = name;
        this.launchDate = launchDate;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.favorite = false;
    }

    @Override
    public String toString() {
        return  "Movie{" +
                "id=" + id +
                ", titleId=" + titleId + '\'' +
                ", name=" + name + '\'' +
                ", Year=" + this.launchDate + '\'' +
                ", Rating=" + this.rating + '\'' +
                ", ImageUrl=" + this.imageUrl + '\'' +
                ", Favorite=" + this.favorite + '\'' +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleId() {return this.titleId; }

    public void setTitleId(String titleId) { this.titleId = titleId; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLaunchDate() {
        return this.launchDate;
    }

    public void setLaunchDate(int launchDate) {
        this.launchDate = launchDate;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
