package db.websocket.service;

import db.websocket.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;



    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontEnd(final String message) {
        ResponseMessage responseMessage = new ResponseMessage(message);
        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", responseMessage);
    }

    public void notifyUser(final String id, final String message) {
        ResponseMessage responseMessage = new ResponseMessage(message);
        notificationService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", responseMessage);
    }
}
