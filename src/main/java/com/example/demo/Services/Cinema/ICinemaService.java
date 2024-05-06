package com.example.demo.Services.Cinema;

import com.example.demo.Entities.Cinema;
import com.example.demo.Exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICinemaService {
    List<Cinema> getCinemaByMovieId(Integer movieId);
    Cinema getCinemaById(Integer cinemaId) throws NotFoundException;
    List <Cinema> findCinemaByAddress(String address);
    String addNewCinema(Cinema cinemaAddnew, MultipartFile image);
    String updateCinema(Cinema cinemaUpdate, MultipartFile image) throws NotFoundException;
    String deleteCinema(Integer id) throws NotFoundException ;
}
