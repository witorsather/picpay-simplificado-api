package com.picpaysimplificadoapi.dtos;

import java.math.BigDecimal;

// dto que representa o payload da solicitação http referente a criação de uma transação
public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {
}
