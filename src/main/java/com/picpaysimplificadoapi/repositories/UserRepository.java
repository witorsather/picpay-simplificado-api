package com.picpaysimplificadoapi.repositories;

import com.picpaysimplificadoapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// classe que iremos usar para manipular a tabela de usuários
@Repository
public interface UserRepository extends JpaRepository<User, Long> {  // JpaRepository recebe entidade que vai manipular e o tipo do id chave primária do User
    // por que só declarei a assinatura do método e não implementei nada, porque o spring data jpa é inteligente o suficiente
    // em runtime(tempo de execução) ele consegue montar a query baseado no nome do método, find achar Usuário por documento

    /**
     * Encontra um usuário com base no número de documento.
     * 
     * Este método utiliza a capacidade do Spring Data JPA de criar consultas automaticamente
     * com base no nome do método. Ele procura um usuário cujo documento corresponda
     * ao valor fornecido como parâmetro.
     *
     * @param document O número de documento do usuário a ser encontrado.
     * @return Um objeto Optional contendo o usuário encontrado, se existir.
     * Caso contrário, retorna um Optional vazio.
     */
    Optional<User> findUserByDocument(String document);

    /**
     * Encontra um usuário com base no id.
     *
     *
     * @param id O número de documento do usuário a ser encontrado.
     * @return Um objeto Optional contendo o usuário encontrado, se existir.
     * Caso contrário, retorna um Optional vazio.
     */
    Optional<User> findUserById(Long id);
}
