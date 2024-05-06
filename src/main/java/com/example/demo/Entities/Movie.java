package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieid")
    private int movieId;

    @Column(name = "moviename")
    private String movieName;

    @Column(name = "movieduration")
    private int movieDuration;

    @Column(name = "premieredate")
    private LocalDate premiereDate;

    @Column(name = "director")
    private String director;

    @Column(name = "actor")
    private String actor;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "image")
    private String image;

    @Column(name = "movietypeid", insertable = false, updatable = false)
    private int movieTypeId;

    @OneToMany(mappedBy = "movie")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Schedule> scheduleList;

    @ManyToOne
    @JoinColumn(name = "movietypeid")
    @JsonIgnore
    private MovieType movieType;

    public Movie(int movieId, String movieName, int movieDuration, LocalDate premiereDate, String director, String actor, String description, String language, int movieTypeId) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDuration = movieDuration;
        this.premiereDate = premiereDate;
        this.director = director;
        this.actor = actor;
        this.description = description;
        this.language = language;
        this.movieTypeId = movieTypeId;
    }
}
