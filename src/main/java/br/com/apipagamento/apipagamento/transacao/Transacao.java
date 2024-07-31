package br.com.apipagamento.apipagamento.transacao;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("TRANSACOES")
public record Transacao(
        @Id
        Long id,
        Long fontePagadora,
        Long fonteBeneficiaria,
        BigDecimal valor,
        @CreatedDate
        LocalDateTime dtTransacao) {

    public Transacao {
        valor = valor.setScale(2);
    }
}