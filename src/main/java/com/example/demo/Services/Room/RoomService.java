package com.example.demo.Services.Room;

import com.example.demo.Entities.Cinema;
import com.example.demo.Entities.Movie;
import com.example.demo.Entities.Room;
import com.example.demo.Entities.Seat;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.CinemaRepository;
import com.example.demo.Repositories.MovieRepository;
import com.example.demo.Repositories.RoomRepository;
import com.example.demo.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Room> getRoom(Integer movieId, Integer cinemaId, LocalDate startDate, LocalTime startTime) throws NotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại");

        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp không tồn tại");

        return roomRepository.getListRoom(movieId, cinemaId, startDate, startTime);
    }

    @Override
    public String addNewRoom(Room newRoom) throws NotFoundException {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(newRoom.getCinemaId());
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp có id " + newRoom.getCinemaId() + " không tồn tại");

        newRoom.setCinema(cinemaOptional.get());

        roomRepository.save(newRoom);

        if(newRoom.getSeatList() != null) {  //Thêm mới room rồi thêm luôn cả list seat của room đó
            for(Seat item : newRoom.getSeatList()) {
                item.setRoom(newRoom);
                seatRepository.save(item);
            }
        }

        return "Thêm mới phòng chiếu thành công";
    }

    @Override
    public String updateRoom(Room roomUpdate) throws NotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(roomUpdate.getRoomId());
        if(roomOptional.isEmpty()) throw new NotFoundException("Phòng chiếu phim không tồn tại để cập nhật");

        Optional<Cinema> cinemaOptional = cinemaRepository.findById(roomUpdate.getCinemaId());
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp chiếu phim không tồn tại");

        roomUpdate.setCinema(cinemaOptional.get());

        roomRepository.save(roomUpdate);

        return "Cập nhật phòng chiếu phim thành công";
    }

    @Override
    public String deleteRoom(Integer id) throws NotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isEmpty()) throw new NotFoundException("Phòng chiếu phim không tồn tại để xoá");

        roomRepository.deleteById(id);
        return "Xoá phòng chiếu phim có id " + id + " thành công";
    }
}
