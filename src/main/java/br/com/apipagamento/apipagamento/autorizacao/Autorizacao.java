package br.com.apipagamento.apipagamento.autorizacao;

public record Autorizacao(
        String memsagem
) {
    public boolean isAutorizado() {
        return memsagem.equals("Autorizado");
    }
}
