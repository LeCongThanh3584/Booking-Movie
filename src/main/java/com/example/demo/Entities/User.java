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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "roleid", insertable = false, updatable = false)
    private int roleId;

    @Column(name = "userstatusid", insertable = false, updatable = false)
    private int userStatusId;

    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<Bill> billList;

    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<ConfirmCode> confirmCodeList;

    @ManyToOne
    @JoinColumn(name = "userstatusid")
    @JsonIgnore
    private UserStatus userStatus;

    @ManyToOne
    @JoinColumn(name = "roleid")
    @JsonIgnore
    private Role role;

    public User(String fullName, String email, String password, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
