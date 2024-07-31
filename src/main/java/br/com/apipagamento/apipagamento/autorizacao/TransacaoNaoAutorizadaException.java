package br.com.apipagamento.apipagamento.autorizacao;

public class TransacaoNaoAutorizadaException extends RuntimeException{
    public TransacaoNaoAutorizadaException(String message) {
        super(message);
    }
}
