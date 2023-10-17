package com.picpaysimplificadoapi.services;

import com.picpaysimplificadoapi.domain.user.User;
import com.picpaysimplificadoapi.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service // service de terceiro que envia notificações para o usuário
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("{$api.notification.transaction.url}")
    String apiNotificationTransactionUrl;

    /**
     * Envia uma notificação por e-mail para um usuário com a mensagem especificada.
     *
     * @param user O usuário para o qual a notificação será enviada.
     * @param message A mensagem a ser incluída na notificação.
     * @throws Exception Se ocorrer um erro ao enviar a notificação ou se o serviço de notificação estiver fora do ar.
     */
    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity(apiNotificationTransactionUrl, notificationRequest, String.class);

        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
            System.out.println("erro ao enviar notificacao");
            throw new Exception("Serviço de notificação está fora do ar");
        }
    }
}
