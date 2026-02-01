package com.solinone.todoc.infrastructure.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class BusinessApiRequest {

    @JsonProperty("b_no")
    private final List<String> businessNumbers;

    public BusinessApiRequest(@JsonProperty("b_no") String businessNumber) {
        this.businessNumbers = List.of(String.valueOf(businessNumber));
    }
}
