package com.solinone.todoc.content.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentBoostRequest {

    @NotNull
    private Long contentId;

    @NotNull
    private Double userLatitude;

    @NotNull
    private Double userLongitude;
}
