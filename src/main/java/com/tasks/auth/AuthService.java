package com.tasks.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tasks.constant.ErrorCode;
import com.tasks.exception.BadRequestException;
import com.tasks.model.User;
import com.tasks.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	private JwtService jwtService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException(
        	        ErrorCode.INVALID_CREDENTIALS
        	    ));

        if (!passwordEncoder.matches(password, user.getPassword())) {  
            throw new BadRequestException(
        	        ErrorCode.INVALID_CREDENTIALS
        	    );
        }

        return jwtService.generateToken(user.getEmail());
    }
}