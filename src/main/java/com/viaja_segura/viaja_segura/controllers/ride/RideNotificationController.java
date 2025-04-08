package com.viaja_segura.viaja_segura.controllers.ride;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class RideNotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/broadcast-ride")
    public ResponseEntity<Void> broadcastRide(@RequestBody Map<String, Object> payload) {
        messagingTemplate.convertAndSend("/topic/ride/request", payload);
        return ResponseEntity.ok().build();
    }
}