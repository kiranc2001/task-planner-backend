package com.taskplanner.serviceImpl;


import com.taskplanner.dto.*;
import com.taskplanner.exception.DuplicateEmailException;
import com.taskplanner.exception.InvalidOtpException;
import com.taskplanner.exception.UserNotFoundException;
import com.taskplanner.helper.EmailHelper;
import com.taskplanner.helper.OtpHelper;
import com.taskplanner.model.User;
import com.taskplanner.repository.UserRepository;
import com.taskplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private EmailHelper emailHelper;
    @Autowired
    private OtpHelper otpHelper;

    @Override
    public UserResponseDto signup(UserSignupDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists: " + dto.getEmail());
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(encoder.encode(dto.getPassword()));
        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public UserResponseDto signin(UserSigninDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + dto.getEmail()));
        if (!encoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        return mapToResponse(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordDto dto) {
        userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + dto.getEmail()));
        String otp = otpHelper.generateOtp();
        otpHelper.storeOtp(dto.getEmail(), otp);
        emailHelper.sendOtpEmail(dto.getEmail(), otp);
    }

    @Override
    public void resetPassword(String email, ResetPasswordDto dto) {
        if (!otpHelper.verifyOtp(email, dto.getOtp())) {
            throw new InvalidOtpException("Invalid or expired OTP");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        user.setPasswordHash(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        otpHelper.removeOtp(email);
    }

    private UserResponseDto mapToResponse(User user) {
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }
}
