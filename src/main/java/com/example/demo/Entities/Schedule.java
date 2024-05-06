package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleid")
    private int scheduleId;

    @Column(name = "price")
    private double price;

    @Column(name = "movieid", insertable = false, updatable = false)
    private int movieId;

    @Column(name = "roomid", insertable = false, updatable = false)
    private int roomId;

    @Column(name = "cinemaid", insertable = false, updatable = false)
    private int cinemaId;

    @Column(name = "startdate")
    private LocalDate startDate;

    @Column(name = "starttime")
    private LocalTime startTime;

    @OneToMany(mappedBy = "schedule")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Ticket> ticketList;

    @ManyToOne
    @JoinColumn(name = "movieid")
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "roomid")
    @JsonIgnore
    private Room room;

    @ManyToOne
    @JoinColumn(name = "cinemaid")
    @JsonIgnore
    private Cinema cinema;

    public Schedule(int scheduleId, double price, int movieId, int roomId, int cinemaId, LocalDate startDate, LocalTime startTime) {
        this.scheduleId = scheduleId;
        this.price = price;
        this.movieId = movieId;
        this.roomId = roomId;
        this.cinemaId = cinemaId;
        this.startDate = startDate;
        this.startTime = startTime;
    }
}
