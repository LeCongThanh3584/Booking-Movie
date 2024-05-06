package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "confirmcode")
public class ConfirmCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirmcodeid")
    private int confirmCodeId;

    @Column(name = "code")
    private String code;

    @Column(name = "expiredtime")
    private LocalDateTime expiredTime;

    @Column(name = "userid", insertable = false, updatable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;
}
