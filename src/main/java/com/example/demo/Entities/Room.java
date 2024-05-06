package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomid")
    private int roomId;

    @Column(name = "roomname")
    private String roomName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "cinemaid", insertable = false, updatable = false)
    private int cinemaId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "room")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Seat> seatList;

    @OneToMany(mappedBy = "room")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Schedule> scheduleList;

    @ManyToOne
    @JoinColumn(name = "cinemaid")
    @JsonIgnore
    private Cinema cinema;

    public Room(int roomId, String roomName, int capacity, int cinemaId, String description, List<Seat> seatList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.cinemaId = cinemaId;
        this.description = description;
        this.seatList = seatList;
    }
}
