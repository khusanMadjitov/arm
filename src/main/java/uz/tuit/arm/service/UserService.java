package uz.tuit.arm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.tuit.arm.entity.Users;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.payload.Status;
import uz.tuit.arm.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ApiResponseModel list() {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            List<Users> allByActive = userRepository.findAllByActive(true);
            apiResponseModel.setData(allByActive);
            status.setCode(200);
            status.setMessage("success");
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error");
        }
        apiResponseModel.setStatus(status);
        return apiResponseModel;
    }

    public ApiResponseModel delete(Long id) {
        ApiResponseModel apiResponseModel = new ApiResponseModel();
        Status status = new Status();
        try {
            Optional<Users> byId = userRepository.findById(id);
            byId.get().setActive(false);
            userRepository.save(byId.get());
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
