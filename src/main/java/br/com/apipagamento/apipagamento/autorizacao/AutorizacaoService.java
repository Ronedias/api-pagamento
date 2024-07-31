package br.com.apipagamento.apipagamento.autorizacao;

import br.com.apipagamento.apipagamento.transacao.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AutorizacaoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutorizacaoService.class);
    private RestClient restClient;

    public AutorizacaoService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(
                "https://run.mocky.io/v3/16615891-412c-45c3-b406-83a216d3a827").build();
    }

    public void autorizacao(Transacao transacao) {
        LOGGER.info("Autorizando transação: {}", transacao );
        var response = restClient.get()
                .retrieve()
                .toEntity(Autorizacao.class);
        if(response.getStatusCode().isError() || !response.getBody().isAutorizado()){
            throw new TransacaoNaoAutorizadaException("Transação não autorizada");
        }
        LOGGER.info("Transação autorizada : {}", transacao );
    }

}
