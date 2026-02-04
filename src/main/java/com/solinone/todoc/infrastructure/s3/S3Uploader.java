package com.solinone.todoc.infrastructure.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.qr-path}")
    private String qrPath;

    /**
     * QR 코드 이미지 S3업로드
     * 같은 placeId로 업로드하면 기존 파일 덮어쓰기
     *
     * @param placeId 장소ID
     * @param imageBytes QR코드 이미지 바이트
     * @return S3 객체 URL
     */

    public String uploadQrImage(Long placeId, byte[] imageBytes) {
        String key = qrPath + "/qr_" + placeId + ".png";

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/png")
                .contentLength((long) imageBytes.length)
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(imageBytes));

        String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
        log.info("QR이미지 S3 업로드 완료 - boardId: {}, url: {}", placeId, url);
        return url;
    }
}
