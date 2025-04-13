package com.viaja_segura.viaja_segura.services.qr_driver;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.viaja_segura.viaja_segura.models.driver.Driver;
import com.viaja_segura.viaja_segura.repositorys.driver.DriverRepository;
import com.viaja_segura.viaja_segura.utils.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrService {
    @Autowired
    private DriverRepository driverRepository;

    public String generateAndSaveDriverQr(Long driverId) throws Exception {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        String content = "{\"driverId\":" + driver.getId() + "}";

        Resource logoResource = new ClassPathResource("static/LOGO_M.png");

        BufferedImage logo = ImageIO.read(logoResource.getInputStream());
        BufferedImage qr = QrCodeGenerator.generateQrWithLogo(content, 500, logo);


        String base64 = QrCodeGenerator.saveImageToBase64(qr);
        driver.setQrCodeBase64(base64);
        driverRepository.save(driver);

        return base64;
    }

    public String getDriverQr(Long driverId) {
        return driverRepository.findById(driverId)
                .map(Driver::getQrCodeBase64)
                .orElseThrow(() -> new RuntimeException("QR no encontrado para este conductor"));
    }
}
