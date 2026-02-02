package com.solinone.todoc.place.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solinone.todoc.place.domain.PlaceType;
import com.solinone.todoc.user.dto.request.ProviderSignupRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCreateRequest {
    @NotBlank(message = "가게 이름은 필수입니다")
    private String placeName;

    @NotNull(message = "가게 유형은 필수입니다")
    private PlaceType placeType;

    @NotNull(message = "위도는 필수입니다")
    private Double latitude;

    @NotNull(message = "경도는 필수입니다")
    private Double longitude;

    @NotBlank(message = "주소는 필수입니다")
    private String address;

    @NotBlank(message = "사업자등록번호는 필수입니다")
    private String businessNumber;

    @JsonFormat(pattern = "yyyy.MM.dd")
    @Schema(
            example = "2026.02.02",
            description = "개업일 (yyyy.MM.dd 형식)"
    )
    @NotNull
    private LocalDate openedAt;

    public static PlaceCreateRequest from(ProviderSignupRequest request) {
        return PlaceCreateRequest.builder()
                .placeName(request.getPlaceName())
                .placeType(request.getPlaceType())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .businessNumber(request.getBusinessNumber())
                .openedAt(request.getOpenedAt())
                .build();
    }
}
