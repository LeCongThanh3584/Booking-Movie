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
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foodid")
    private int foodId;

    @Column(name = "foodname")
    private String foodName;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "food")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<BillFood> billFoodList;

    public Food(int foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }
}
