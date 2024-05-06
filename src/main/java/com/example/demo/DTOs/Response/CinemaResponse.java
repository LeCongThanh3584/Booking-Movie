package com.example.demo.DTOs.Response;

import com.example.demo.Entities.Room;
import com.example.demo.Entities.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
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

public class CinemaResponse {
    private int cinemaId;

    private String cinemaName;

    private String address;

    private String phoneNumber;

    private String image;

}
