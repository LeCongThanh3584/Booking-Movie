package com.example.demo.Services.CloudinaryService;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map uploadImage(MultipartFile file)  {  //Upload ảnh lên server
        try{
            Map data = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }

    public void deleteImageBySecureUrl(String secureUrl) { // Xóa ảnh từ Cloudinary
        // Xác định public ID từ secure URL
        String publicId = extractPublicId(secureUrl);

        try {
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            throw new RuntimeException("Image deletion failed");
        }
    }

    private String extractPublicId(String secureUrl) {
        String[] urlParts = secureUrl.split("/");
        String publicIdWithExtension = urlParts[urlParts.length - 1];
        String[] publicIdParts = publicIdWithExtension.split("\\.");
        return publicIdParts[0];
    }
}
