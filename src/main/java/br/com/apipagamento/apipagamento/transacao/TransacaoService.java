package br.com.apipagamento.apipagamento.transacao;

import br.com.apipagamento.apipagamento.autorizacao.AutorizacaoService;
import br.com.apipagamento.apipagamento.carteira.Carteira;
import br.com.apipagamento.apipagamento.carteira.CarteiraRepository;
import br.com.apipagamento.apipagamento.carteira.TipoCarteira;
import br.com.apipagamento.apipagamento.notificacao.NotificacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final CarteiraRepository carteiraRepository;
    private final AutorizacaoService autorizacaoService;
    private final NotificacaoService notificacaoService;

    public TransacaoService(TransacaoRepository transacaoRepository, CarteiraRepository carteiraRepository, AutorizacaoService autorizacaoService, NotificacaoService notificacaoService) {
        this.transacaoRepository = transacaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.autorizacaoService = autorizacaoService;
        this.notificacaoService = notificacaoService;
    }

    @Transactional
    public Transacao criarTransacao(Transacao transacao) {
        //1-validar
        validarTransacao(transacao);

        //2-criar a transação
        var novaTransacao = transacaoRepository.save(transacao);

        //3-debitar da carteira
        var carteiraPagador = carteiraRepository.findById(transacao.fontePagadora()).get();
        var carteiraRecebedor = carteiraRepository.findById(transacao.fonteBeneficiaria()).get();
        carteiraRepository.save(carteiraPagador.debito(transacao.valor()));
        carteiraRepository.save(carteiraRecebedor.addCredito(transacao.valor()));


        //chamar serviços externos
        //autorização da transação
        autorizacaoService.autorizacao(transacao);

        notificacaoService.enviarNotificacao(transacao);
        return novaTransacao;

    }

    //regras//
    //pagador tem carteira do tipo comum
    //pagador tem saldo suficiente
    //pagador nao pode ser o recebedor


    //payer = fonte pagadora
    //payee  = fonte beneficiaria
    private void validarTransacao(Transacao transacao) {
//      busca id do recebedor
        carteiraRepository.findById(transacao.fonteBeneficiaria())
                //mapea o resultado para consultar o pagador
                .map(fonteBeneficiaria -> carteiraRepository.findById(transacao.fontePagadora())
                        //mapeia novamente o resutado e usa o pagor para verificar
                        .map(fontePagadora -> seTransacaoValida(transacao, fontePagadora) ? transacao : null)
                        .orElseThrow(() -> new TransacaoInvalidaException("Transacao Invalida - %s".formatted(transacao))))
                .orElseThrow(() -> new TransacaoInvalidaException("Transacao Invalida - %s".formatted(transacao)));
    }

    private boolean seTransacaoValida(Transacao transacao, Carteira fontePagadora) {
        return fontePagadora.tipoCarteira() == TipoCarteira.COMUM.getValor() && fontePagadora.saldoCarteira().compareTo(transacao.valor()) >= 0 && !fontePagadora.id().equals(transacao.fonteBeneficiaria());
    }

    public List<Transacao> list() {
        return transacaoRepository.findAll();
    }
}
