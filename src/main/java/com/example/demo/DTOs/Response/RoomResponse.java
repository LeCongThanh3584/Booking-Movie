package com.example.demo.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RoomResponse {

    private int roomId;

    private String roomName;

    private int capacity;

    private String description;
}
