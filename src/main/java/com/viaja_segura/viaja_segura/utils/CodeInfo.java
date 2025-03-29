package com.viaja_segura.viaja_segura.utils;

import java.time.LocalDateTime;

public class CodeInfo {
    public String code;
    public LocalDateTime expiresAt;

    public CodeInfo(String code, LocalDateTime expiresAt) {
        this.code = code;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
