package br.com.apipagamento.apipagamento.transacao;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public Transacao criaTransacao(@RequestBody Transacao transacao){
        return transacaoService.criarTransacao(transacao);
    }

    @GetMapping
    public List<Transacao> list(){
        return transacaoService.list();
    }
}
