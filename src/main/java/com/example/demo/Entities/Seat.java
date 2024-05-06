package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatid")
    private int seatId;

    @Column(name = "seatname")
    private String seatName;

    @Column(name = "roomid", insertable = false, updatable = false)
    private int roomId;

    @OneToMany(mappedBy = "seat")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Ticket> ticketList;

    @ManyToOne
    @JoinColumn(name = "roomid")
    @JsonIgnore
    private Room room;

    public Seat(int seatId, String seatName, int roomId) {
        this.seatId = seatId;
        this.seatName = seatName;
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatId == seat.seatId;
    }
}
