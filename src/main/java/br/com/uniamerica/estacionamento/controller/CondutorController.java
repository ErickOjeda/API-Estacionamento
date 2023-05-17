package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private CondutorService condutorService;

    //  GET By Id, All e By Ativo
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id){
        Condutor condutor  = this.condutorRepository.findById(id).orElse(null);
        return condutor == null
                ? ResponseEntity.badRequest().body("Nenhum registro encontrado")
                : ResponseEntity.ok(condutor);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.condutorRepository.findAll());
    }

    @GetMapping("/listaAtivos")
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.condutorRepository.findAtivos());
    }

//  POST
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try{
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok(condutor);
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

//  PUT
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Condutor condutor) {
        try{
            this.condutorService.editar(id, condutor);
            return ResponseEntity.ok(condutor);
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }


//    DELETE -> Se o condutor estiver vinculado a uma movimentação então ativo = false senão delete
    @DeleteMapping
    public ResponseEntity <?> deletar (@RequestParam ("id") final Long id) {
        try {
            this.condutorService.deletar(id);
            return ResponseEntity.ok("Deletado com sucesso");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
