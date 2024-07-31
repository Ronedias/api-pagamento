package br.com.apipagamento.apipagamento.carteira;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("CARTEIRAS")
public record Carteira(
        @Id
        Long id,
        String nomeCompleto,
        Long cpf,
        String email,
        String password,
        int tipoCarteira,
        BigDecimal saldoCarteira) {

    public Carteira debito(BigDecimal valor) {
        return new Carteira(id,nomeCompleto,cpf,email,password,tipoCarteira, saldoCarteira.subtract(valor));
    }

    public Carteira addCredito(BigDecimal valor) {
        return new Carteira(id,nomeCompleto,cpf,email,password,tipoCarteira, saldoCarteira.add(valor));
    }
}
