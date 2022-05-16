package uz.tuit.arm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.tuit.arm.entity.Category;
import uz.tuit.arm.entity.Users;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.payload.ReqCategory;
import uz.tuit.arm.payload.Status;
import uz.tuit.arm.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponseModel list() {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            List<Category> all = categoryRepository.findAll();
            apiResponseModel.setData(all);
            status.setCode(200);
            status.setMessage("success");
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }

    public ApiResponseModel add(String category) {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            Category category1 = new Category();
            category1.setName(category);
            categoryRepository.save(category1);
            status.setCode(200);
            status.setMessage("success");
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }
}
