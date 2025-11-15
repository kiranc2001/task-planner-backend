package com.taskplanner.controller;

import com.taskplanner.dto.*;
import com.taskplanner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserResponseDto signup(@Valid @RequestBody UserSignupDto dto) {
        return userService.signup(dto);
    }

    @PostMapping("/signin")
    public UserResponseDto signin(@Valid @RequestBody UserSigninDto dto) {
        return userService.signin(dto);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordDto dto) {
        userService.forgotPassword(dto);
    }

    @PostMapping("/{email}/reset-password")
    public void resetPassword(@PathVariable String email, @Valid @RequestBody ResetPasswordDto dto) {
        userService.resetPassword(email, dto);
    }
}
