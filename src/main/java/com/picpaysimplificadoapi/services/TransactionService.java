package com.picpaysimplificadoapi.services;

import com.picpaysimplificadoapi.domain.transaction.Transaction;
import com.picpaysimplificadoapi.domain.user.User;
import com.picpaysimplificadoapi.dtos.TransactionDTO;
import com.picpaysimplificadoapi.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service // indicar para o spring que é uma classe de serviço
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository; // dependências dessa classe

    @Autowired
    private RestTemplate restTemplate; // RestTemplate é uma classe que o spring nos oferece, ela é responsável por faver comunicação http entre serviço,  pode fazer através delas chamadas http tipo get, put

    @Autowired
    private NotificationService notificationService;

    @Value("{$api.authorize.transaction.url}")
    String apiAuthorizeTransactionUrl;

    /**
     * Cria uma nova transação com base nas informações fornecidas no objeto TransactionDTO.
     *
     * @param transaction O objeto TransactionDTO contendo detalhes da transação.
     * @return A transação criada e persistida com sucesso.
     * @throws Exception Se ocorrer algum erro durante o processamento da transação.
     */
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        // Pega o remetente e o destinatário da transação
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        // Realiza a primeira validação para verificar se a transação pode ser realizada entre o remetente e o destinatário
        userService.validateTransaction(sender, transaction.value());

        // Realiza a segunda validação consultando a API de validação de transações para autorização
        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());

        if (!isAuthorized) {
            throw new Exception("Transação não autorizada.");
        }

        // Cria uma nova instância de transação com os detalhes fornecidos
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        // Atualiza os saldos do remetente e destinatário
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        // Persiste a nova transação e atualiza os saldos dos usuários remetente e destinatário
        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        // Notifica o remetente e o destinatário sobre a transação
        /* Api de notificação fora do ar
        String senderMessage = String.format(
                "Você enviou R$ %.2f para %s. Seu novo saldo é R$ %.2f.",
                transaction.value(), receiver.getFirstName() + " " + receiver.getLastName(), sender.getBalance()
        );
        String receiverMessage = String.format(
                "Você recebeu R$ %.2f de %s. Seu novo saldo é R$ %.2f.",
                transaction.value(), sender.getFirstName() + " " + sender.getLastName(), receiver.getBalance()
        );

        this.notificationService.sendNotification(sender, senderMessage);
        this.notificationService.sendNotification(receiver, receiverMessage);
         */
        return newTransaction;
    }


    /**
     * Autoriza uma transação para um determinado usuário.
     *
     * @param sender O usuário remetente da transação.
     * @param value  O valor da transação a ser autorizado.
     * @return Verdadeiro se a transação for autorizada, falso caso contrário.
     */
    public boolean authorizeTransaction(User sender, BigDecimal value) {
        return true;
        /* api fora do ar

        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(apiAuthorizeTransactionUrl, Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
        
         */
    }
}
