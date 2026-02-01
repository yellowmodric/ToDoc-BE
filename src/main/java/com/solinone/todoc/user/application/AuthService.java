package com.solinone.todoc.user.application;

import com.solinone.todoc.global.exception.ErrorCode;
import com.solinone.todoc.user.domain.User;
import com.solinone.todoc.user.domain.UserRole;
import com.solinone.todoc.user.dto.request.VisitorSignupRequest;
import com.solinone.todoc.user.exception.DuplicateEmailException;
import com.solinone.todoc.user.infrastructure.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUpVisitor(@Valid VisitorSignupRequest request) {
        validateDuplicateEmail(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.VISITOR)
                .build();

        userRepository.save(user);
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}
