package com.example.demo.DTOs.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieRequest {

    private int movieId;

    @NotBlank(message = "Tên phim không được để trống")
    private String movieName;

    @NotNull(message = "Thời lượng phim không được để trống")
    private Integer movieDuration;

    private LocalDate premiereDate;

    @NotBlank(message = "Đạo diễn không được để trống")
    private String director;

    private String actor;

    private String description;

    private String language;

    @NotNull(message = "Thể loại phim không được để trống")
    private Integer movieTypeId;
}
