package com.solinone.todoc.user.application;

import com.solinone.todoc.global.security.CustomUserDetails;
import com.solinone.todoc.global.security.jwt.JwtTokenProvider;
import com.solinone.todoc.place.application.PlaceService;
import com.solinone.todoc.place.domain.Place;
import com.solinone.todoc.place.dto.request.PlaceCreateRequest;
import com.solinone.todoc.place.infrastructure.PlaceRepository;
import com.solinone.todoc.user.domain.User;
import com.solinone.todoc.user.domain.UserRole;
import com.solinone.todoc.user.dto.request.LoginRequest;
import com.solinone.todoc.user.dto.request.ProviderSignupRequest;
import com.solinone.todoc.user.dto.request.VisitorSignupRequest;
import com.solinone.todoc.user.dto.response.LoginResponse;
import com.solinone.todoc.user.exception.DuplicateEmailException;
import com.solinone.todoc.user.exception.InvalidCredentialException;
import com.solinone.todoc.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaceService placeService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PlaceRepository placeRepository;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

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
            throw new DuplicateEmailException();
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
        placeService.createPlaceOnSignup(savedUser, placeRequest);
    }

    public LoginResponse login(LoginRequest request) {
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));

            String accessToken = jwtTokenProvider.generateToken(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            List<Long> placeIds = null;
            if (userDetails.getUser().getRole() == UserRole.PROVIDER) {
                placeIds = placeRepository.findAllByUserUserId(userDetails.getUserId())
                        .stream()
                        .map(Place::getPlaceId)
                        .collect(Collectors.toList());
            }
            log.info("로그인 성공 - userId: {}, email: {} ", userDetails.getUserId(), request.getEmail());

            return LoginResponse.of(accessToken, jwtExpiration / 1000, userDetails, placeIds);
        } catch (AuthenticationException e) {
            log.error("로그인 실패: {}", request.getEmail(), e);
            throw new InvalidCredentialException();
        }
    }
}
