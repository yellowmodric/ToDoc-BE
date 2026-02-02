package com.solinone.todoc.place.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solinone.todoc.place.domain.PlaceType;
import com.solinone.todoc.user.dto.request.ProviderSignupRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
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
    private LocalDate openedAt;

    public static PlaceCreateRequest from(ProviderSignupRequest request) {
        return PlaceCreateRequest.builder()
                .placeName(request.getPlaceName())
                .placeType(PlaceType.RESTAURANT)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .businessNumber(request.getBusinessNumber())
                .openedAt(request.getOpenedAt())
                .build();
    }
}
