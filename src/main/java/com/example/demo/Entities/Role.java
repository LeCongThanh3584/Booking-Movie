package com.example.demo.Entities;


import com.example.demo.Enum.ERole;
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
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private int roleId;

    @Column(name = "rolename")
    @Enumerated(EnumType.STRING)
    private ERole roleName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private List<User> userList;
}
