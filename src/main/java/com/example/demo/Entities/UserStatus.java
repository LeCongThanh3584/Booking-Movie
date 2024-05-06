package com.example.demo.Entities;

import com.example.demo.Enum.EUserStatus;
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
@Table(name = "userstatus")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userstatusid")
    private int userStatusId;

    @Column(name = "userstatusname")
    @Enumerated(EnumType.STRING)
    private EUserStatus userStatusName;

    @OneToMany(mappedBy = "userStatus")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<User> userList;
}
