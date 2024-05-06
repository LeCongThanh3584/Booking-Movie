package com.example.demo.Services.FoodService;

import com.example.demo.DTOs.Request.FoodRequest;
import com.example.demo.Entities.Food;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.FoodRepository;
import com.example.demo.Services.CloudinaryService.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public String addNewFood(Food newFood, MultipartFile image) {
        //Upload ảnh lên cloudinary rồi lấy đường dẫn ảnh về
        Map data = cloudinaryService.uploadImage(image);
        String linkImage = (String) data.get("secure_url");

        newFood.setImage(linkImage);
        foodRepository.save(newFood);
        return "Thêm mới đồ ăn thành công";
    }

    @Override
    public String updateFood(Food foodUpdate, MultipartFile image) throws NotFoundException {
        Optional<Food> foodOptional = foodRepository.findById(foodUpdate.getFoodId());
        if(foodOptional.isEmpty()) throw new NotFoundException("Đồ ăn không tồn tại để cập nhật");

        if(image == null) {
            foodUpdate.setImage(foodOptional.get().getImage());
        }else {
            //Upload ảnh lên cloudinary rồi lấy đường dẫn ảnh về
            Map data = cloudinaryService.uploadImage(image);
            String linkImage = (String) data.get("secure_url");

            foodUpdate.setImage(linkImage);
        }

        foodRepository.save(foodUpdate);

        return "Cập nhật đồ ăn thành công";
    }

    @Override
    public String deleteFood(Integer foodId) throws NotFoundException {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if(foodOptional.isEmpty()) throw new NotFoundException("Đồ ăn không tồn tại để xoá");

        cloudinaryService.deleteImageBySecureUrl(foodOptional.get().getImage()); //Xoá ảnh trên cloudinary
        foodRepository.deleteById(foodId); //Xoá food dưới db
        return "Xoá đồ ăn có id " + foodId + " thành công";
    }
}
