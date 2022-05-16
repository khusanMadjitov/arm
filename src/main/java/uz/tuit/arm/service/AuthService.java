package uz.tuit.arm.service;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.tuit.arm.entity.Permission;
import uz.tuit.arm.entity.Users;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.payload.SignIn;
import uz.tuit.arm.payload.SignUp;
import uz.tuit.arm.payload.Status;
import uz.tuit.arm.repository.PermissionRepository;
import uz.tuit.arm.repository.UserRepository;
import uz.tuit.arm.security.JwtTokenProvider;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@CommonsLog
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;


    public UserDetails loadUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id not found: " + userId));
    }

    public ApiResponseModel addUser(SignUp signUp) {
        try {
            if (signUp.getFirstName() == null) {
                return new ApiResponseModel(new Status("first name mustn't be empty", 500));
            }
            if (signUp.getPermissions().size() == 0) {
                return new ApiResponseModel(new Status("Choose one permission at least", 500));
            }
            if (!userRepository.existsByPhoneNumber(signUp.getPhoneNumber())) {
                Users user = new Users();
                user.setFirstName(signUp.getFirstName());
                user.setLastName(signUp.getLastName());
                user.setActive(true);
                user.setPhoneNumber(signUp.getPhoneNumber());
                user.setPassword(passwordEncoder.encode(signUp.getPassword()));
                List<Permission> permissions = new ArrayList<>();
                for (int i = 0; i < signUp.getPermissions().size(); i++) {
                    Optional<Permission> byId = permissionRepository.findById(signUp.getPermissions().get(i));
                    byId.ifPresent(permissions::add);
                }
                user.setPermissions(permissions);
                userRepository.save(user);
                return new ApiResponseModel(new Status("Success", 200));
            } else {
                return new ApiResponseModel(new Status("phone number already exist !", 500));
            }
        } catch (Exception e) {
            return new ApiResponseModel(new Status("Error", 500));
        }
    }

    public ApiResponseModel login(SignIn signIn) {
        ApiResponseModel response = new ApiResponseModel();
        Status status = new Status();
        try {
            if (userRepository.existsByPhoneNumber(signIn.getPhoneNumber())) {
                status.setCode(200);
                status.setMessage("success !");
                response.setData(getApiToken(signIn.getPhoneNumber(), signIn.getPassword()));
            } else {
                status.setCode(401);
                status.setMessage("number not found !");
            }
        } catch (Exception e) {
            status.setCode(500);
            status.setMessage("error !");
        }
        response.setStatus(status);
        return response;
    }

//    public ApiResponseModel userMe(Users users) {
//        ApiResponseModel res = new ApiResponseModel();
//        Status status = new Status();
//        try {
//            UserMe userMe = new UserMe();
//            userMe.setId(users.getId());
//            userMe.setFirstName(users.getFirstName());
//            userMe.setLastName(users.getLastName());
//            userMe.setAttachmentId(users.getAttachment() != null ? users.getAttachment().getId() : null);
//            userMe.setPoliceType(users.getPoliceType() != null ? users.getPoliceType().getPoliceTypeEnum().toString() : null);
//            boolean b = carRepository.existsCarByUsers(users);
//            userMe.setPhoneNumber(users.getPhoneNumber());
//            userMe.setLocationExch(!b);
//            status.setCode(200);
//            status.setMessage("success");
//            res.setData(userMe);
//        } catch (Exception e) {
//            status.setCode(500);
//            status.setMessage("error");
//        }
//        res.setStatus(status);
//        return res;
//    }

    public String getApiToken(String phoneNumber, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return jwt;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findUsersByPhoneNumber(phoneNumber);
    }

    public ApiResponseModel getByPageable(int page, int size) {
        try {
            ApiResponseModel apiResponseModel = new ApiResponseModel();
            List<Map<String, Object>> list = jdbcTemplate.queryForList(
                    "select u.id,\n" +
                            "       u.created_at,\n" +
                            "       u.created_by,\n" +
                            "       u.updated_at,\n" +
                            "       u.updated_at,\n" +
                            "       u.account_non_expired,\n" +
                            "       u.account_non_locked,\n" +
                            "       u.active,\n" +
                            "       u.credentials_non_expired,\n" +
                            "       u.dig_tash_token,\n" +
                            "       u.enabled,\n" +
                            "       u.first_name,\n" +
                            "       u.last_name,\n" +
                            "       u.middle_name,\n" +
                            "       u.password,\n" +
                            "       u.phone_number,\n" +
                            "       u.boshqarma_id\n" +
                            "from users u\n" +
                            "where u.is_police = false\n" +
                            "offset ? limit ?", page, size);
            apiResponseModel.setData(list);
            apiResponseModel.setStatus(new Status("ok", 200));
            return apiResponseModel;
        } catch (Exception e) {
            return new ApiResponseModel(new Status("error", 500), new ArrayList<>());
        }
    }

//    @Scheduled(cron = "*/15 * * * * *")
//    public void sendAllCarsToOperator() {
//        LinkedList<Users> operator = userRepository.findUsersByIsOperator(true);
//        List<ResCurloc> curloc = mongoTemplate.findAll(ResCurloc.class, "curloc");
//        for (int i = 0; i < operator.size(); i++) {
//            messageSendingService.sendToOperators(curloc, operator.get(i).getId().toString());
//        }
//    }


//    public ResUsers getUser(Users users) {
//        return new ResUsers(
//                users.getId(),
//                users.getFirstName(),
//                users.getLastName(),
//                users.getMiddleName(),
//                users.getRank(),
//                users.getPhoneNumber(),
//                users.getPassportNumber(),
//                users.getAttachment() != null ? users.getAttachment().getId() : null,
//                users.getLongitude(),
//                users.getLatitude(),
//                users.getPoliceType() != null ? users.getPoliceType().getId() : null,
//                users.getImei(),
//                users.getGpsStatus(),
//                users.getRank(),
//                users.getIsBusy()
//        );
//    }

//    public ApiResponseModel editUser(MultipartHttpServletRequest request, Users users) {
//        ApiResponseModel apiResponseModel = new ApiResponseModel();
//        try {
//            if (request.getFile("file") != null) {
//                Attachment attachment = attachmentService.uploadFile1(request);
//                Attachment save = attachmentRepository.save(attachment);
//                String phoneNumber = request.getParameter("phoneNumber");
//                users.setAttachment(save);
//                users.setPhoneNumber(phoneNumber);
//                userRepository.save(users);
//                apiResponseModel.setStatus(new Status("Photo changed", 200));
//                apiResponseModel.setData(users);
//                return apiResponseModel;
//            } else {
//                users.setPhoneNumber(request.getParameter("phoneNumber"));
//                userRepository.save(users);
//                apiResponseModel.setStatus(new Status("number saved", 200));
//                return apiResponseModel;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


}
