package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "movietype")
public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movietypeid")
    private int movieTypeId;

    @Column(name = "movietypename")
    private String movieTypeName;

    @OneToMany(mappedBy = "movieType")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Movie> movieList;

    public MovieType(int movieTypeId, String movieTypeName) {
        this.movieTypeId = movieTypeId;
        this.movieTypeName = movieTypeName;
    }
}
