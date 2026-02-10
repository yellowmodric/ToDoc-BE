package com.solinone.todoc.user.dto.response;

import com.solinone.todoc.global.security.CustomUserDetails;
import com.solinone.todoc.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private Long userId;
    private String email;
    private String nickname;
    private UserRole role;
    private List<Long> placeIds;

    public static LoginResponse of (String accessToken, Long expiresIn,
                                    CustomUserDetails userDetails, List<Long> placeIds) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .userId(userDetails.getUserId())
                .email(userDetails.getUsername())
                .nickname(userDetails.getNickname())
                .role(userDetails.getUser().getRole())
                .placeIds(placeIds)
                .build();
    }
}
