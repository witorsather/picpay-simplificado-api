package com.picpaysimplificadoapi.controllers;

import com.picpaysimplificadoapi.domain.transaction.Transaction;
import com.picpaysimplificadoapi.dtos.TransactionDTO;
import com.picpaysimplificadoapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * Cria uma nova transação com base nos detalhes fornecidos em um objeto TransactionDTO.
     * Realiza validações, atualiza saldos de contas de remetente e destinatário, persiste a transação e notifica as partes envolvidas.
     *
     * @param transaction O objeto TransactionDTO contendo os detalhes da transação.
     * @return A transação criada com sucesso e sua resposta HTTP correspondente indicando o status 201 (Created).
     * @throws Exception Se ocorrer algum erro durante o processamento da transação.
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(transaction);
        
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
