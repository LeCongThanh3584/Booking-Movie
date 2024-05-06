package com.example.demo.Services.Movie;

import com.example.demo.Entities.Movie;
import com.example.demo.Entities.MovieType;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.MovieRepository;
import com.example.demo.Repositories.MovieTypeRepository;
import com.example.demo.Services.CloudinaryService.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MovieTypeRepository movieTypeRepository;

    @Override
    public Page<Movie> getAllMovieAndPagination(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);  //Mỗi trang lấy ra 10 bản ghi
        return movieRepository.findAllByOrderByMovieIdDesc(pageable);
    }

    @Override
    public Movie getMovieById(Integer movieId) throws NotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại");

        return movieOptional.get();
    }

    @Override
    public List<Movie> searchMovie(String keyword) {
        return movieRepository.findByMovieNameContaining(keyword);
    }

    @Override
    public String addNewMovie(Movie movieAddNew, MultipartFile image) throws NotFoundException {
        Optional<MovieType> movieTypeOptional = movieTypeRepository.findById(movieAddNew.getMovieTypeId());
        if(movieTypeOptional.isEmpty()) throw new NotFoundException("Thể loại phim không tồn tại");

        // Upload ảnh lên cloudinary và lấy đường link hình ảnh về
        Map data = cloudinaryService.uploadImage(image);
        String linkImage = (String) data.get("secure_url");

        movieAddNew.setImage(linkImage);
        movieAddNew.setMovieType(movieTypeOptional.get());

        movieRepository.save(movieAddNew);

        return "Thêm mới phim thành công";
    }

    @Override
    public String updateMovie(Movie movieUpdate, MultipartFile image) throws NotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(movieUpdate.getMovieId());
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại để cập nhật");

        Optional<MovieType> movieTypeOptional = movieTypeRepository.findById(movieUpdate.getMovieTypeId());
        if(movieTypeOptional.isEmpty()) throw new NotFoundException("Thể loại phim không tồn tại");

        if(image == null) { //Không gửi ảnh lên thì lấy ảnh cũ
            movieUpdate.setImage(movieOptional.get().getImage());
        } else { //Gửi ảnh lên thì lấy ảnh mới
            Map data = cloudinaryService.uploadImage(image);
            String linkImage = (String) data.get("secure_url");

            movieUpdate.setImage(linkImage);
        }

        movieUpdate.setMovieType(movieTypeOptional.get());
        movieRepository.save(movieUpdate);

        return "Cập nhật phim có id " + movieUpdate.getMovieId() + " thành công";
    }

    @Override
    public String deleteMovie(Integer movieId) throws NotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()) throw new NotFoundException("Phim không tồn tại để xoá");

        cloudinaryService.deleteImageBySecureUrl(movieOptional.get().getImage()); //Xoá ảnh ở cloudinary
        movieRepository.deleteById(movieId); //Xoá phim
        return "Xoá phim có id " + movieId + " thành công";
    }
}
