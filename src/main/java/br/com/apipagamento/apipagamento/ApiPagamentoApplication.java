package br.com.apipagamento.apipagamento;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

@EnableJdbcAuditing //habilita auditoria no BD
@SpringBootApplication
public class ApiPagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPagamentoApplication.class, args);
	}

	@Bean
	NewTopic topicNotificacao() {
		return TopicBuilder.name("transaction-notification").build();
	}
}
