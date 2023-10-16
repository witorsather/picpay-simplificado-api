package com.picpaysimplificadoapi.domain.transaction;

import com.picpaysimplificadoapi.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions") // transaction costuma ser uma palavra reservada em bancos de dados sql
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // não tem tanto problema ser 1,2,3, mas no usuário não é legal usar IDENTIFY
    private Long id;
    private BigDecimal amount; // valor da transação transferida de um usuário para o outro
    @ManyToOne // anotação jpa que cria um relacionamento entre a tabela transactions e users e diz que é muitas transactions para apenas um usuário
    @JoinColumn(name = "sender_id") // chave estrangeira que irá fazer junção com a tabela Users no campo id dela, sem definir o nome personalizado seria user_id que é a tabela alvo mais o campo id
    private User sender; // remetente
    @ManyToOne // anotação jpa que cria um relacionamento entre a tabela transactions e users e diz que é muitas transactions para apenas um usuário
    @JoinColumn(name = "receiver_id")
    private User receiver; // destinatário
    private LocalDateTime timestamp; // carimbo de tempo, o horário exato que foi realizado essa transação
    
}
