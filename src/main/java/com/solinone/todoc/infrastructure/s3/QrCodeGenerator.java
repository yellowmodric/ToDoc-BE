package com.solinone.todoc.infrastructure.s3;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QrCodeGenerator {

    /**
     * QR코드 이미지를 바이트 배열로 생성
     * @param url QR에 넣을 URL
     * @param width 가로 크기(px)
     * @param height 세로 크기(px)
     * @return PNG 이미지 바이트 배열
     */
    public static byte[] generateQrCode(String url, int width, int height) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height);
            BufferedImage image = matrixToImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            return baos.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("QR코드 생성 실패", e);
        }
    }
    private static BufferedImage matrixToImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, matrix.get(i, j) ? 0x000000: 0xFFFFFF);
            }
        }
        return image;
    }
}
