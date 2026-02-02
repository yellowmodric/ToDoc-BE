package com.solinone.todoc.user.application;

import com.solinone.todoc.global.exception.ErrorCode;
import com.solinone.todoc.place.application.PlaceService;
import com.solinone.todoc.place.dto.PlaceCreateRequest;
import com.solinone.todoc.user.domain.User;
import com.solinone.todoc.user.dto.request.ProviderSignupRequest;
import com.solinone.todoc.user.dto.request.VisitorSignupRequest;
import com.solinone.todoc.user.exception.DuplicateEmailException;
import com.solinone.todoc.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaceService placeService;

    @Transactional
    public void signUpVisitor( VisitorSignupRequest request) {
        validateDuplicateEmail(request.getEmail());

        User user = User.createVisitor(
                request.getNickname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
    }

    public void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    @Transactional
    public void signUpProvider(ProviderSignupRequest request) {
        validateDuplicateEmail(request.getEmail());

        User user = User.createProvider(
                request.getName(),
                request.getNickname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        User savedUser = userRepository.save(user);
        log.info("사장님 회원가입 완료 - userId: {}" , savedUser.getUserId());

        PlaceCreateRequest placeRequest = PlaceCreateRequest.from(request);
        placeService.createPlace(savedUser, placeRequest);
    }
}
