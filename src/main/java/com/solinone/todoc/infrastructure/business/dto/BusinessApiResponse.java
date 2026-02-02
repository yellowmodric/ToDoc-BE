package com.solinone.todoc.infrastructure.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BusinessApiResponse {

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("data")
    private List<BusinessData> data;

    @Getter
    @NoArgsConstructor
    public static class BusinessData {

        @JsonProperty("b_no")
        private String businessNumber;

        @JsonProperty("b_stt")
        private String status;

        @JsonProperty("b_stt_cd")
        private String statusCode;

        @JsonProperty("tax_type")
        private String taxType;

        @JsonProperty("tax_type_cd")
        private String taxTypeCode;
    }
}
