package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    //  GET By Id, All e By Ativo
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id) {
        Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
        return veiculo == null
                ? ResponseEntity.badRequest().body("Nenhum registro encontrado")
                : ResponseEntity.ok(veiculo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }

    @GetMapping("/listaAtivos")
    public ResponseEntity<?> getAtivos() {
        return ResponseEntity.ok(this.veiculoRepository.findByAtivos());
    }

    //  POST
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo) {

        try {
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok(veiculo);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }

    }

    //  PUT
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo) {
        try {
            this.veiculoService.editar(id, veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }


    //    DELETE -> Se o Veiculo estiver vinculado a uma movimentação então ativo = false senão delete
    @DeleteMapping
    public ResponseEntity<?> deletar(@RequestParam("id") final Long id) {

        try {
            this.veiculoService.deletar(id);
            return ResponseEntity.ok("Deletado");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }
}