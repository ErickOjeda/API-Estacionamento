package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.Relatorio;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/mov")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MovimentacaoService movimentacaoService;

    //  GET By Id, All e By Abertas
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id) {

        final Movimentacao mov = this.movimentacaoRepository.findById(id).orElse(null);

        return mov == null
                ? ResponseEntity.badRequest().body("Nenhum registro encontrado")
                : ResponseEntity.ok(mov);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> lista() {
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }

    @GetMapping("/listaAtivos")
    public ResponseEntity<?> listaAbertas() {

        List<Movimentacao> movs = this.movimentacaoRepository.findAbertas();
        return ResponseEntity.ok(movs);
    }


    // POST
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try {
            this.movimentacaoService.cadastrar(movimentacao);

            return ResponseEntity.ok(movimentacao);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }

    //  PUT
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao)
    {
        try {
            this.movimentacaoService.editar(id, movimentacao);
            return ResponseEntity.ok(movimentacao);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @PutMapping("/sair/{id}")
    public ResponseEntity <?> sair (@PathVariable("id") final Long id) {
        try {
            Relatorio relatorio = this.movimentacaoService.sair(id);
            return ResponseEntity.ok(relatorio);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

    }



    //    DELETE
    @DeleteMapping
    public ResponseEntity <?> deletar (@RequestParam ("id") final Long id) {
        try {
            this.movimentacaoService.deletar(id);
            return ResponseEntity.ok("Deletado");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

    }

    }
