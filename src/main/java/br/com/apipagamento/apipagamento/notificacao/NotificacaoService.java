package br.com.apipagamento.apipagamento.notificacao;

import br.com.apipagamento.apipagamento.transacao.Transacao;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    private final ProdutorNotificacao produtorNotificacao;

    public NotificacaoService(ProdutorNotificacao produtorNotificacao) {
        this.produtorNotificacao = produtorNotificacao;
    }

    //usando  abordagem assincrona
    //trabalar com o kafka
    public void enviarNotificacao(Transacao transacao){
        produtorNotificacao.enviarNotificacao(transacao);
    }
}
