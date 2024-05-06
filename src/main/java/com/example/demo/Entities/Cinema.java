package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinemaid")
    private int cinemaId;

    @Column(name = "cinemaname")
    private String cinemaName;

    @Column(name = "address")
    private String address;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "cinema")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Room> roomList;

    @OneToMany(mappedBy = "cinema")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Schedule> scheduleList;

    public Cinema(int cinemaId, String cinemaName, String address, String phoneNumber, List<Room> roomList) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.roomList = roomList;
    }
}
