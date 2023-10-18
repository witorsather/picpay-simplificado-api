package com.picpaysimplificadoapi.infra;

import com.picpaysimplificadoapi.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Este método lida com exceções de violação de integridade de dados.
     * É ativado quando há uma tentativa de inserir ou atualizar dados no banco que violariam
     * regras de integridade, como campos únicos.
     *
     * @param exception A exceção de violação de integridade de dados lançada.
     * @return Uma resposta HTTP com status 400 (Bad Request) e detalhes sobre a exceção.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    // estou usando uma classe do próprio jpa, em vez de eu criar a minha própria classe de Exception estendendo Exception
    public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", "400"); // foi passado um documento ou email que já está cadastrado pois eles estão unique = true no jpa

        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    /**
     * Este método lida com exceções de entidades não encontradas.
     * É ativado quando uma busca por uma entidade no banco de dados não retorna resultados.
     *
     * @param exception A exceção de entidade não encontrada lançada.
     * @return Uma resposta HTTP com status 404 (Not Found), indicando que a entidade solicitada não foi encontrada.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Este método lida com todas as exceções não especificamente capturadas por outros manipuladores de exceção.
     * É uma espécie de "rede de segurança" que captura qualquer tipo de exceção que possa ocorrer.
     *
     * @param exception A exceção genérica lançada.
     * @return Uma resposta HTTP com status 500 (Internal Server Error) e detalhes sobre a exceção.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");

        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

    // em createTransaction o método faz isso throw new Exception("Transação não autorizada.");, o meu threatGeneralException irá capturar essa excesão e retornar o erro 500 com a mensagem "Transação não autorizada", evitando mostrar a pilha de erro pro usuário
}
