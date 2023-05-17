package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/config")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Autowired
    private ConfiguracaoService configuracaoService;

//    GET
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(this.configuracaoRepository.findById(id));
    }

//    POST
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao config){
        try{
            this.configuracaoService.cadastrar(config);
            return ResponseEntity.ok(config);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getStackTrace());
        }
    }

//    PUT
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Configuracao configuracao) {
        try {
            this.configuracaoService.editar(id, configuracao);
            return ResponseEntity.ok(configuracao);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }
}
