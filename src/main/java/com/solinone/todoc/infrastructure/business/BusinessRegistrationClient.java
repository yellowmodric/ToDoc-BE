package com.solinone.todoc.infrastructure.business;

import com.solinone.todoc.global.exception.ErrorCode;
import com.solinone.todoc.infrastructure.business.dto.BusinessApiRequest;
import com.solinone.todoc.infrastructure.business.dto.BusinessApiResponse;
import com.solinone.todoc.infrastructure.business.exception.InvalidBusinessNumberException;
import com.solinone.todoc.user.dto.response.BusinessVerificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessRegistrationClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${business.api.key}")
    private String apiKey;

    @Value("${business.api.url}")
    private String apiUrl;

    public BusinessVerificationResponse verifyBusinessNumber(String businessNumber) {
        try {
            log.info("사업자등록번호 검증 시작: {}", businessNumber);

            String url = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("serviceKey", apiKey)
                    .toUriString();
            log.info("API URL: {}", url);

            Map<String, Object> requestBody = Map.of("b_no", List.of(businessNumber));

            BusinessApiResponse response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(BusinessApiResponse.class)
                    .block();

            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                return BusinessVerificationResponse.invalid(
                        businessNumber,
                        "존재하지 않는 사업자등록번호입니다."
                );
            }

            BusinessApiResponse.BusinessData data = response.getData().get(0);
            String statusCode = data.getStatusCode();

            if ("01".equals(statusCode)) {
                log.info("사업자 검증 성공: {}", businessNumber);
                return BusinessVerificationResponse.valid(businessNumber);
            }

            String message = getErrorMessage(statusCode, data.getStatus());
            log.warn("사업자 검증 실패: {} - {}", businessNumber, message);
            return BusinessVerificationResponse.invalid(businessNumber, message);
        } catch (Exception e) {
            log.error("사업자 검증 API 호출 실패: {}", businessNumber, e);
            throw new InvalidBusinessNumberException(ErrorCode.INVALID_BUSINESS_NUMBER);
        }
    }
    private String getErrorMessage(String statusCode, String status) {
        return switch (statusCode) {
            case "02" -> "휴업중인 사업자입니다.";
            case "03" -> "폐업한 사업자입니다.";
            case "" -> "존재하지 않는 사업자등록번호입니다.";
            default -> status != null && !status.isEmpty()
                    ? status + "상태의 사업자입니다."
                    : "유효하지 않은 사업자등록번호입니다.";
        };
    }
}


