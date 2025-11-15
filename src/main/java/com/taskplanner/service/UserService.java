package com.taskplanner.service;

import com.taskplanner.dto.*;

public interface UserService {
    UserResponseDto signup(UserSignupDto dto);
    UserResponseDto signin(UserSigninDto dto);
    void forgotPassword(ForgotPasswordDto dto);
    void resetPassword(String email, ResetPasswordDto dto);
}
