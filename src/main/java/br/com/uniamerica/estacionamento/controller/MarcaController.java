package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaService marcaService;

    //  GET By Id, All e By Ativo
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id) {

        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("Nenhum registro encontrado")
                : ResponseEntity.ok(marca);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> lista() {
        return ResponseEntity.ok(this.marcaRepository.findAll());
    }

    @GetMapping("/listaAtivos")
    public ResponseEntity<?> listaAtivos() {

        List<Marca> marcas = this.marcaRepository.findByAtivos();
        return ResponseEntity.ok(marcas);
    }

//  POST
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try {
            this.marcaService.cadastrar(marca);
            return ResponseEntity.ok(marca);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

//  PUT
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marca)
    {
        try {
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if (marcaBanco == null || !marcaBanco.getId().equals(marca.getId())) {
                throw new RuntimeException("Não foi possivel identificar o registro informado ");
            }

            this.marcaRepository.save(marca);
            return ResponseEntity.ok(marca);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }


    //    DELETE -> Se a Marca estiver vinculada a uma marca então ativo = false senão delete
    @DeleteMapping
    public ResponseEntity <?> deletar (@RequestParam ("id") final Long id) {
       try{
            this.marcaService.deletar(id);
            return ResponseEntity.ok("Deletado");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

}

