package uz.tuit.arm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.payload.SignIn;
import uz.tuit.arm.payload.SignUp;
import uz.tuit.arm.repository.UserRepository;
import uz.tuit.arm.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    @PostMapping("/add")
    public HttpEntity<?> register(@RequestBody SignUp signUp) {
        ApiResponseModel response = authService.addUser(signUp);
        return ResponseEntity.status(response.getStatus().getCode() == 200 ? HttpStatus.OK : HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody SignIn signIn) {
        ApiResponseModel response = authService.login(signIn);
        return ResponseEntity.status(response.getStatus().getCode() == 200 ? HttpStatus.OK : HttpStatus.UNAUTHORIZED).body(response);
    }


//    @GetMapping("/getAll")
//    public HttpEntity<?> get(@CurrentUser Users users) {
//        ApiResponseModel response = authService.getOneUser(users);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//
//    @PutMapping("/editUser")
//    public HttpEntity<?> updateUser(MultipartHttpServletRequest multipartHttpServletRequest, @CurrentUser Users users) {
//        ApiResponseModel apiResponseModel = authService.editUser(multipartHttpServletRequest, users);
//        return ResponseEntity.ok(apiResponseModel);
//    }
}
