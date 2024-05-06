package com.example.demo.Services.Cinema;

import com.example.demo.Entities.Cinema;
import com.example.demo.Entities.Room;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.CinemaRepository;
import com.example.demo.Repositories.RoomRepository;
import com.example.demo.Repositories.ScheduleRepository;
import com.example.demo.Services.CloudinaryService.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CinemaService implements ICinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<Cinema> getCinemaByMovieId(Integer movieId) {
        LocalDate now = LocalDate.now(); //Lấy ngày hiện tại
        return cinemaRepository.findAllByMovieId(movieId, now);  //tìm những rạp nào chiếu phim từ ngày hiện tại trở đi
    }

    @Override
    public Cinema getCinemaById(Integer cinemaId) throws NotFoundException {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp chiếu phim không tồn tại");

        return cinemaOptional.get();
    }

    @Override
    public List<Cinema> findCinemaByAddress(String address) {
        return cinemaRepository.findByAddressContaining(address);
    }

    @Override
    public String addNewCinema(Cinema cinemaAddnew, MultipartFile image) {
        //Upload ảnh lên cloudinary và lấy đường link hình ảnh về
        Map data = cloudinaryService.uploadImage(image);

        String imageLink = (String) data.get("secure_url");
        cinemaAddnew.setImage(imageLink);

        cinemaRepository.save(cinemaAddnew);

        //Thêm cinema thì thêm luôn list room của cinema đó, lặp từng room rồi lưu vào db
        if(cinemaAddnew.getRoomList() != null) {
            for(Room item : cinemaAddnew.getRoomList()) {
                item.setCinema(cinemaAddnew);
                roomRepository.save(item);
            }
        }

        return "Thêm mới rạp thành công";
    }

    @Override
    public String updateCinema(Cinema cinemaUpdate, MultipartFile image) throws NotFoundException {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaUpdate.getCinemaId());
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp không tồn tại để cập nhật");

        if (image == null) {
            cinemaUpdate.setImage(cinemaOptional.get().getImage());
        } else {
            Map data = cloudinaryService.uploadImage(image);
            String imageLink = (String) data.get("secure_url");
            cinemaUpdate.setImage(imageLink);
        }

        cinemaRepository.save(cinemaUpdate);

        return "Cập nhật rạp thành công";
    }

    @Override
    public String deleteCinema(Integer id) throws NotFoundException {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        if(cinemaOptional.isEmpty()) throw new NotFoundException("Rạp không tồn tại");

        cloudinaryService.deleteImageBySecureUrl(cinemaOptional.get().getImage()); //Xoá ảnh ở trên cloudinary
        cinemaRepository.deleteById(id);  //Xoá rạp
        return "Xoá rạp " + cinemaOptional.get().getCinemaName() + " thành công";
    }
}
