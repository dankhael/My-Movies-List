package com.mymovieslist.My.Movies.List.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = true)
    private Long id;
    private String titleId;
    private String name;
    private String launchDate;
    private String rating;
    private String imageUrl;

    private Boolean favorite;

    public Movie() {}

    public Movie(String title_id, String name, String launch_date, String rating, String imageUrl) {
        this.titleId = title_id;
        this.name = name;
        this.launchDate = launch_date;
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
                ", Year=" + this.getLaunchDate() + '\'' +
                ", Rating=" + this.getRating() + '\'' +
                ", ImageUrl=" + this.getImageUrl() + '\'' +
                ", Favorite=" + this.getFavorite() + '\'' +
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getTitleId() {return titleId; }

    public void setTitleId(String title_id) { titleId = title_id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
