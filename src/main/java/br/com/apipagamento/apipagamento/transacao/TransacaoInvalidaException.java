package br.com.apipagamento.apipagamento.transacao;

public class TransacaoInvalidaException extends RuntimeException {
    public TransacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
