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
@Table(name = "billfood")
public class BillFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billfoodid")
    private int billFoodId;

    @Column(name = "billid", insertable = false, updatable = false)
    private int billId;

    @Column(name = "foodid", insertable = false, updatable = false)
    private int foodId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "billid")
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "foodid")
    @JsonIgnore
    private Food food;
}
