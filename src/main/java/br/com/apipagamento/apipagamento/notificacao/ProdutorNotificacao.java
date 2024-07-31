package br.com.apipagamento.apipagamento.notificacao;

import br.com.apipagamento.apipagamento.transacao.Transacao;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProdutorNotificacao {
    private final KafkaTemplate<String, Transacao> kafkaTemplate;

    public ProdutorNotificacao(KafkaTemplate<String, Transacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarNotificacao(Transacao transacao) {
        kafkaTemplate.send("transaction-notification", transacao);
    }
}
