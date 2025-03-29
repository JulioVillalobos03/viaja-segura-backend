package com.viaja_segura.viaja_segura.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetCodeEmail(String to, String subject, String code) {
        try {
            String[] digits = code.split("");

            String content = loadTemplate("templates/reset-code-email.html")
                    .replace("{{dig1}}", digits[0])
                    .replace("{{dig2}}", digits[1])
                    .replace("{{dig3}}", digits[2])
                    .replace("{{dig4}}", digits[3])
                    .replace("{{dig5}}", digits[4])
                    .replace("{{dig6}}", digits[5])
                    .replace("{{email}}", to);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            // Agregar logo (ajusta la ruta si usas otro nombre o ubicación)
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/images/Logo_VS.png"));
            helper.addInline("logoImage", logo);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo: " + e.getMessage());
        }
    }

    public void sendUserRegistrationEmail(String to, String name, String lastName, String email, String role) {
        try {
            String content = loadTemplate("templates/user-registered-email.html")
                    .replace("{{name}}", name)
                    .replace("{{lastName}}", lastName)
                    .replace("{{email}}", email)
                    .replace("{{role}}", role);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setSubject("Bienvenido a Viaja Segura");
            helper.setText(content, true);
            helper.addInline("logoImage", new ClassPathResource("static/images/Logo_VS.png")); // asegúrate de tener este archivo

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo de bienvenida: " + e.getMessage());
        }
    }

    private String loadTemplate(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) throw new FileNotFoundException("No se encontró la plantilla: " + path);
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

}
