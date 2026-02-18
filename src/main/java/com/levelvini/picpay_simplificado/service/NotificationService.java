package com.levelvini.picpay_simplificado.service;

import com.levelvini.picpay_simplificado.dtos.NotificationDTO;
import com.levelvini.picpay_simplificado.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void  sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email,message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify",notificationRequest, String.class);
        if(notificationResponse.getStatusCode() != HttpStatus.OK){
            System.out.println("Notification cannot been sent ");
            throw new Exception("Notification service its not working");
        }
    }
}
