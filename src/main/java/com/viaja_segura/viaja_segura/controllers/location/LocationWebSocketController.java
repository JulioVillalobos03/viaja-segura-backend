package com.viaja_segura.viaja_segura.controllers.location;

import com.viaja_segura.viaja_segura.dtos.location.LocationMessage;
import com.viaja_segura.viaja_segura.dtos.websocket.LocationUpdateDto;
import com.viaja_segura.viaja_segura.models.location.WebSocketLocationMessage;
import com.viaja_segura.viaja_segura.services.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/location/update")
    public void updateLocation(LocationUpdateDto dto) {
        locationService.updateLocation(dto);

        messagingTemplate.convertAndSend("/topic/location", dto);
    }
}
