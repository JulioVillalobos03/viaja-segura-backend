package com.viaja_segura.viaja_segura.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class QrCodeGenerator {

    public static BufferedImage generateQrWithLogo(String content, int size, BufferedImage logo) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints);

        int qrColor = new Color(113, 120, 91).getRGB(); // vino
        int bgColor = Color.WHITE.getRGB();
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix, new MatrixToImageConfig(qrColor, bgColor));

        int logoWidth = size / 3;
        int logoHeight = size / 3;

        Image scaledLogo = logo.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);

        Graphics2D g = qrImage.createGraphics();
        int centerX = (qrImage.getWidth() - logoWidth) / 2;
        int centerY = (qrImage.getHeight() - logoHeight) / 2;

        int padding = 10;
        g.setColor(Color.WHITE);
        g.fillRoundRect(centerX - padding / 2, centerY - padding / 2,
                logoWidth + padding, logoHeight + padding, 10, 10);

        g.drawImage(scaledLogo, centerX, centerY, null);
        g.dispose();

        return qrImage;
    }


    public static String saveImageToBase64(BufferedImage image) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
