package com.example.demo.Services.MovieType;

import com.example.demo.Entities.MovieType;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.MovieTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieTypeService implements IMovieTypeService {

    @Autowired
    private MovieTypeRepository movieTypeRepository;

    @Override
    public List<MovieType> getAllMovieType() {
        return movieTypeRepository.findAll();
    }

    @Override
    public String addNewMovieType(MovieType movieTypeAddnew) {
        movieTypeRepository.save(movieTypeAddnew);
        return "Thêm mới thể loại phim thành công";
    }

    @Override
    public String updateMovieType(MovieType movieTypeUpdate) throws NotFoundException {
        Optional<MovieType> movieTypeOptional = movieTypeRepository.findById(movieTypeUpdate.getMovieTypeId());
        if(movieTypeOptional.isEmpty()) throw new NotFoundException("Thể loại phim không tồn tại để cập nhật");

        movieTypeRepository.save(movieTypeUpdate);
        return "Cập nhật thể loại phim thành công";
    }

    @Override
    public String deleteMovieType(Integer id) throws NotFoundException {
        Optional<MovieType> movieTypeOptional = movieTypeRepository.findById(id);
        if(movieTypeOptional.isEmpty()) throw new NotFoundException("Thể loại phim không tồn tại để xoá");

        movieTypeRepository.deleteById(id);
        return "Xoá thể loại phim có id " + id + " thành công";
    }
}
