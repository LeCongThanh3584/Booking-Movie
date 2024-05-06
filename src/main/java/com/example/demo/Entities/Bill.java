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
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billid")
    private int billId;

    @Column(name = "totalprice")
    private double totalPrice;

    @Column(name = "tradingcode")
    private String tradingCode;

    @Column(name = "createtime")
    private LocalDateTime createTime;

    @Column(name = "userid", insertable = false, updatable = false)
    private int userId;

    @OneToMany(mappedBy = "bill")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Ticket> ticketList;

    @OneToMany(mappedBy = "bill")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<BillFood> billFoodList;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;
}
