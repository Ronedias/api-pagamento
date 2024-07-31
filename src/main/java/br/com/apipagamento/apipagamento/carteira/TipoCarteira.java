package br.com.apipagamento.apipagamento.carteira;

public enum TipoCarteira {
    COMUM(1), LOJISTA(2);

    private int valor;

    private TipoCarteira(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
