package com.example.demo.DTOs.Request;

import com.example.demo.Entities.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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

public class CinemaRequest {
    private int cinemaId;

    @NotBlank(message = "Tên rạp không thể bỏ trống")
    private String cinemaName;

    @NotBlank(message = "Địa chỉ không thể bỏ trống")
    private String address;

    @NotBlank(message = "Số điện thoại không thể bỏ trống")
    private String phoneNumber;

    private List<Room> roomList;
}
