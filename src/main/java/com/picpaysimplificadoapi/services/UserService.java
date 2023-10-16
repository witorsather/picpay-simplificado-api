package com.picpaysimplificadoapi.services;

import com.picpaysimplificadoapi.domain.user.User;
import com.picpaysimplificadoapi.domain.user.UserType;
import com.picpaysimplificadoapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
// indica pro spring que é uma classe de serviço o que o ajuda a depois fazer as injeções em outras classes de forma correta
public class UserService {
    @Autowired // fala para o spring fazer a injeção de uma instância de repository aqui pra eu poder usar os métodos da instância
    UserRepository userRepository;


    /**
     * Valida uma transação entre um remetente e um valor desejado.
     *
     * Este método verifica se o tipo de usuário do remetente não é um Lojista
     * e se o saldo do remetente é suficiente para a transação desejada.
     *
     * @param sender O usuário remetente da transação.
     * @param amount O valor desejado para a transação.
     * @throws Exception Lança uma exceção se o remetente for do tipo Lojista
     *                   ou se o saldo for insuficiente para a transação.
     */
    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo Lojista não está autorizado a realizar uma transação");
        }

        // Compara o saldo do remetente com o valor desejado de envio.
        // Se o saldo for menor que o valor desejado, lança uma exceção.
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    // TransactionService só irá manipular transactions, não usuários, então por isso preciso criar um método de buscar usuário
    // no UserService, separação de responsabilidades, se o TransactionService quer manipular a tabela de usuários usa o UserService


    /**
     * Busca um usuário pelo ID.
     *
     * @param id O ID do usuário a ser encontrado.
     * @return O usuário encontrado, se existir.
     * @throws Exception Se o usuário não for encontrado.
     */
    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }
    
    /**
     * Salva um usuário no repositório.
     *
     * @param user O usuário a ser salvo.
     */
    public void saveUser(User user) {
        this.userRepository.save(user);  // só pra persistir as alterações(instância User passada no parâmetro) no usuário
    }
    // após atualizar o balance saldo do usuário precisa persistir no banco de dados, pois o balance é um atributo do User
}
