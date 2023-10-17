package com.picpaysimplificadoapi.domain.user;

import com.picpaysimplificadoapi.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name ="users") // user costuma ser uma palavra resertava dos bancos de dados
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String document; // cpf
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance; // saldo da conta, seria melhor se fosse uma tabela separada com relacionamento OneToMany já que um usuário pode ter mais de uma conta e um saldo
    @Enumerated(EnumType.STRING) // se eu não passar por padrão ele irá usar integer o que é ruim de visualizar no banco
    private UserType userType; // tipo de usuário comum ou logista

    public User(UserDTO userDTO) {
        this.firstName = userDTO.firstName();
        this.lastName = userDTO.lastName();
        this.document = userDTO.document();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.balance = userDTO.balance();
        this.userType = userDTO.userType();
    }
}
