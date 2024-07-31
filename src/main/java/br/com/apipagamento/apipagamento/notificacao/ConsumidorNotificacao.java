package br.com.apipagamento.apipagamento.notificacao;


import br.com.apipagamento.apipagamento.transacao.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ConsumidorNotificacao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumidorNotificacao.class);

    public RestClient restClient;
    public ConsumidorNotificacao(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://run.mocky.io/v3/5a5869dc-a8f2-4082-8028-0a7149e447ea").build();
    }


    @KafkaListener(topics = "transaction-notification", groupId = "api-pagamento-backend")
    public void receberNotificao(Transacao transacao){
        LOGGER.info("Notificando transação {}...", transacao);
        var response = restClient.get()
                .retrieve()
                .toEntity(Notificacao.class);

        if (response.getStatusCode().isError() || !response.getBody().message()){
            throw new NotificaoException("Error ao enviar a notificação!");
        }
        LOGGER.info("Notificação foi enviada com sucesso {}...", transacao);
    }
}
