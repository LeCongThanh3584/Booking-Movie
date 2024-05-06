package com.example.demo.Services.Schedule;

import com.example.demo.Entities.Cinema;
import com.example.demo.Entities.Movie;
import com.example.demo.Entities.Room;
import com.example.demo.Entities.Schedule;
import com.example.demo.Exceptions.InvalidDataException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.CinemaRepository;
import com.example.demo.Repositories.MovieRepository;
import com.example.demo.Repositories.RoomRepository;
import com.example.demo.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements IScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Schedule getScheduleForBuyTicket(Integer movieId, Integer cinemaId, LocalDate startDate, LocalTime startTime, Integer roomId) throws NotFoundException {
        Optional<Schedule> scheduleOptional = scheduleRepository.getScheduleByMovieIdAndCinemaIdAndStartDateAndStartTimeAndRoomId(movieId, cinemaId, startDate, startTime, roomId);
        if(scheduleOptional.isEmpty()) throw new NotFoundException("Lịch chiếu phim không tồn tại");

        return scheduleOptional.get();
    }

    @Override
    public List<String> getListStartTime(Integer movieId, Integer cinemaId, LocalDate startDate) throws NotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại");

        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp không tồn tại");

        List<String> startTimeList = scheduleRepository.getStartTimeByMovieIdAndCinemaIdAndStartDate(movieId, cinemaId, startDate)
                .stream().map(item -> item.format(DateTimeFormatter.ofPattern("HH:mm"))).collect(Collectors.toList());

        return startTimeList;
    }

    @Override
    public List<Schedule> getScheduleByCinemaId(Integer cinemaId, LocalDate startDate) {

        return scheduleRepository.findByCinemaIdAndStartDate(cinemaId, startDate);
    }

    @Override
    public String addNewSchedule(Schedule scheduleAddnew) throws NotFoundException, InvalidDataException {
        Optional<Movie> movieOptional = movieRepository.findById(scheduleAddnew.getMovieId());
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại");

        Optional<Room> roomOptional = roomRepository.findById(scheduleAddnew.getRoomId());
        if(roomOptional.isEmpty()) throw new NotFoundException("Phòng chiếu không tồn tại");

        Optional<Cinema> cinemaOptional = cinemaRepository.findById(scheduleAddnew.getCinemaId());
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp chiếu phim không không tồn tại");

        if(scheduleAddnew.getStartDate().isBefore(LocalDate.now())) throw new InvalidDataException("Ngày chiếu phim phải từ ngày hiện tại trở đi");

        if(scheduleAddnew.getStartTime().isBefore(LocalTime.now()) && scheduleAddnew.getStartDate().isEqual(LocalDate.now()))
            throw new InvalidDataException("Thời gian chiếu phải từ giờ hiện tại trở đi");

        scheduleAddnew.setCinema(cinemaOptional.get());
        scheduleAddnew.setRoom(roomOptional.get());
        scheduleAddnew.setMovie(movieOptional.get());

        scheduleRepository.save(scheduleAddnew);

        return "Thêm mới lịch chiếu phim thành công";
    }

    @Override
    public String updateSchedule(Schedule scheduleUpdate) throws NotFoundException, InvalidDataException {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleUpdate.getScheduleId());
        if(scheduleOptional.isEmpty()) throw new NotFoundException("Lịch chiếu phim không tồn tại để cập nhật");

        Optional<Movie> movieOptional = movieRepository.findById(scheduleUpdate.getMovieId());
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại");

        Optional<Room> roomOptional = roomRepository.findById(scheduleUpdate.getRoomId());
        if(roomOptional.isEmpty()) throw new NotFoundException("Phòng chiếu không tồn tại");

        Optional<Cinema> cinemaOptional = cinemaRepository.findById(scheduleUpdate.getCinemaId());
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp chiếu phim không không tồn tại");

        if(scheduleUpdate.getStartDate().isBefore(LocalDate.now())) throw new InvalidDataException("Ngày chiếu phim phải từ ngày hiện tại trở đi");

        if(scheduleUpdate.getStartTime().isBefore(LocalTime.now()) && scheduleUpdate.getStartDate().isEqual(LocalDate.now()))
            throw new InvalidDataException("Thời gian chiếu phải từ giờ hiện tại trở đi");

        scheduleUpdate.setMovie(movieOptional.get());
        scheduleUpdate.setRoom(roomOptional.get());
        scheduleUpdate.setCinema(cinemaOptional.get());

        scheduleRepository.save(scheduleUpdate);
        return "Cập nhật lịch chiếu phim có id " + scheduleUpdate.getScheduleId() + " thành công";
    }

    @Override
    public String deleteSchedule(Integer scheduleId) throws NotFoundException {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        if(scheduleOptional.isEmpty()) throw new NotFoundException("Lịch chiếu phim không tồn tại để xoá");

        scheduleRepository.deleteById(scheduleId);
        return "Xoá lịch chiếu phim có id " + scheduleId + " thành công";
    }
}
