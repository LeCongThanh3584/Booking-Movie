package com.example.demo.Services.User;

import com.example.demo.DTOs.Request.ChangePasswordRequest;
import com.example.demo.DTOs.Request.ResetPasswordRequest;
import com.example.demo.DTOs.Request.UserRequest;
import com.example.demo.Entities.ConfirmCode;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserStatus;
import com.example.demo.Enum.ERole;
import com.example.demo.Enum.EUserStatus;
import com.example.demo.Exceptions.ExistException;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ConfirmCodeRepository;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.UserStatusRepository;
import com.example.demo.Services.Email.EmailService;
import com.example.demo.Utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private ConfirmCodeRepository confirmCodeRepository;

    @Override
    public String register(User user) throws NotFoundException, ExistException {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()) throw new ExistException("Email đã tồn tại");

        Optional<Role> roleOptional = roleRepository.findByRoleName(ERole.USER);
        if(roleOptional.isEmpty()) throw new NotFoundException("Quyền user không tồn tại");

        Optional<UserStatus> userStatusOptional = userStatusRepository.findByUserStatusName(EUserStatus.NOT_ACTIVATED);
        if(userStatusOptional.isEmpty()) throw new NotFoundException("Trạng thái chưa kích hoạt không tồn tại");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleOptional.get());
        user.setUserStatus(userStatusOptional.get());

        userRepository.save(user);

        String code = GenerateCodeUtil.generateCode();
        ConfirmCode confirmCode = new ConfirmCode();
        confirmCode.setCode(code);
        confirmCode.setUser(user);
        confirmCode.setExpiredTime(LocalDateTime.now().plusMinutes(3));

        confirmCodeRepository.save(confirmCode);

        emailService.sendEmail(user.getEmail(), "Kích hoạt tài khoản", code);

        return "Đăng ký tài khoản thành công";
    }

    @Override
    public String activeAccount(String code) {
        Optional<ConfirmCode> confirmCodeOptional = confirmCodeRepository.findByCode(code);
        if(confirmCodeOptional.isEmpty()) return "Mã xác nhận không đúng";

        if(confirmCodeOptional.get().getExpiredTime().isBefore(LocalDateTime.now())) return "Mã xác nhận đã hết hạn";

        Optional<UserStatus> userStatusOptional = userStatusRepository.findByUserStatusName(EUserStatus.ACTIVATED);
        if(userStatusOptional.isEmpty()) return "Trạng thái kích hoạt không tồn tại";

        confirmCodeOptional.get().getUser().setUserStatus(userStatusOptional.get());
        userRepository.save(confirmCodeOptional.get().getUser());

        return "Kích hoạt tài khoản thành công";
    }

    @Override
    public String sendCodeToEmail(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) throw new NotFoundException("Email không tồn tại");

        String code = GenerateCodeUtil.generateCode();
        ConfirmCode confirmCode = new ConfirmCode();
        confirmCode.setCode(code);
        confirmCode.setUser(userOptional.get());
        confirmCode.setExpiredTime(LocalDateTime.now().plusMinutes(3));

        confirmCodeRepository.save(confirmCode);

        emailService.sendEmail(email, "Mã xác nhận đặt lại mật khẩu", code);

        return "Gửi mã xác nhận đổi mật khẩu về email thành công";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) throws Exception {
        Optional<ConfirmCode> confirmCodeOptional = confirmCodeRepository.findByCode(request.getCode());
        if(confirmCodeOptional.isEmpty()) throw new Exception("Mã xác nhận không đúng");

        if(confirmCodeOptional.get().getExpiredTime().isBefore(LocalDateTime.now()))
            throw new Exception("Mã xác nhận đã hết hạn");

        if(!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new Exception("Mật khẩu mới và xác nhận mật khẩu mới không giống nhau");

        confirmCodeOptional.get().getUser().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(confirmCodeOptional.get().getUser());

        return "Đặt lại mật khẩu thành công";

    }

    @Override
    public String updateInfor(Integer userId, UserRequest request) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new NotFoundException("Người dùng cập nhật thông tin không tồn tại");

        userOptional.get().setFullName(request.getFullName());
        userOptional.get().setPhoneNumber(request.getPhoneNumber());

        userRepository.save(userOptional.get());
        return "Cập nhật thông tin cá nhân thành công";
    }

    @Override
    public String changePassword(Integer userId, ChangePasswordRequest request) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new Exception("Người dùng không tồn tại");

        if(!passwordEncoder.matches(request.getCurrentPassword(), userOptional.get().getPassword()))
            throw new Exception("Mât khẩu hiện tại không đúng");

        if(!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new Exception("Mật khẩu mới và xác nhận mật khẩu mới không giống nhau");

        userOptional.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(userOptional.get());

        return "Cập nhật mật khẩu thành công";
    }
}
