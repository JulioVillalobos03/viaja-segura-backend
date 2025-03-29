package com.viaja_segura.viaja_segura.controllers.auth;

import com.viaja_segura.viaja_segura.dtos.auth.PasswordResetRequestDto;
import com.viaja_segura.viaja_segura.dtos.auth.PasswordResetValidationDto;
import com.viaja_segura.viaja_segura.dtos.auth.PasswordUpdateDto;
import com.viaja_segura.viaja_segura.services.auth.InMemoryPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private InMemoryPasswordResetService resetService;

    @PostMapping("/request")
    public ResponseEntity<String> requestCode(@RequestBody PasswordResetRequestDto dto) {
        resetService.sendResetCode(dto.email);
        return ResponseEntity.ok("Código enviado al correo");
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestBody PasswordResetValidationDto dto) {
        boolean valid = resetService.validateCode(dto.email, dto.code);
        return ResponseEntity.ok(valid ? "Código válido" : "Código inválido");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestBody PasswordUpdateDto dto) {
        resetService.updatePassword(dto.email, dto.code, dto.newPassword);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
