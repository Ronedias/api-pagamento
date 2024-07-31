package br.com.apipagamento.apipagamento.transacao;

import org.springframework.data.repository.ListCrudRepository;

public interface TransacaoRepository extends ListCrudRepository<Transacao, Long> {
}
