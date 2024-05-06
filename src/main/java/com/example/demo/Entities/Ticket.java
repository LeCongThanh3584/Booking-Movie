package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketid")
    private int ticketId;

    @Column(name = "billid", insertable = false, updatable = false)
    private int billId;

    @Column(name = "seatid", insertable = false, updatable = false)
    private int seatId;

    @Column(name = "scheduleid", insertable = false, updatable = false)
    private int scheduleId;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "billid")
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "seatid")
    @JsonIgnore
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "scheduleid")
    @JsonIgnore
    private Schedule schedule;

}
