package com.example.demo.Services.Seat;

import com.example.demo.DTOs.Response.SeatResponse;
import com.example.demo.Entities.Room;
import com.example.demo.Entities.Schedule;
import com.example.demo.Entities.Seat;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mapper.SeatMapper;
import com.example.demo.Repositories.RoomRepository;
import com.example.demo.Repositories.ScheduleRepository;
import com.example.demo.Repositories.SeatRepository;
import com.example.demo.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<SeatResponse> getSeatsByScheduleId(Integer scheduleId) throws NotFoundException {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        if(scheduleOptional.isEmpty()) throw new NotFoundException("Lịch chiếu phim không tồn tại");

        Room room = scheduleOptional.get().getRoom();
        List<Seat> seatList = room.getSeatList(); //Lấy ra tât cả các seat trong phòng chiếu lịch đó

        List<Seat> listSeatOccupied = ticketRepository.findByScheduleId(scheduleId).stream().map(
                item -> item.getSeat()
        ).collect(Collectors.toList());  //Lấy ra tất cả các seat đã được đặt trong lịch chiếu đó

        List<SeatResponse> seatResponseList = seatList.stream().map(
                item -> {
                    SeatResponse seatResponse = SeatMapper.mapFromEntityToResponse(item);
                    if(listSeatOccupied.contains(item)) seatResponse.setAvailable(false);
                    return seatResponse;
                }
        ).collect(Collectors.toList());

        return seatResponseList;
    }

    @Override
    public String addNewSeat(Seat newSeat) throws NotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(newSeat.getRoomId());
        if(roomOptional.isEmpty()) throw new NotFoundException("Phòng chiếu có id " + newSeat.getRoomId() + " không tồn tại");

        newSeat.setRoom(roomOptional.get());

        seatRepository.save(newSeat);

        return "Thêm mới ghế ngồi cho phòng chiếu có id " + newSeat.getRoomId() + " thành công";
    }

    @Override
    public String updateSeat(Seat seatUpdate) throws NotFoundException {
        Optional<Seat> seatOptional = seatRepository.findById(seatUpdate.getSeatId());
        if(seatOptional.isEmpty()) throw new NotFoundException("Chỗ ngồi không tồn tại để cập nhật");

        Optional<Room> roomOptional = roomRepository.findById(seatUpdate.getRoomId());
        if(roomOptional.isEmpty()) throw new NotFoundException("Phòng chiếu không tồn tại");

        seatUpdate.setRoom(roomOptional.get());

        seatRepository.save(seatUpdate);

        return "Cập nhật chỗ ngồi thành công";
    }

    @Override
    public String deleteSeat(Integer seatId) throws NotFoundException {
        Optional<Seat> seatOptional = seatRepository.findById(seatId);
        if(seatOptional.isEmpty()) throw new NotFoundException("Chỗ ngồi không tồn tại để xoá");

        seatRepository.deleteById(seatId);
        return "Xoá chỗ ngồi có id " + seatId + " thành công";
    }
}
