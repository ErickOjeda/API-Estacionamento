package br.com.uniamerica.estacionamento.controller;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ModeloService modeloService;

//  GET By Id, All e By Ativo
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id) {

        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("Nenhum registro encontrado")
                : ResponseEntity.ok(modelo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> lista() {
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }

    @GetMapping("/listaAtivos")
    public ResponseEntity<?> listaAtivos() {

        List<Modelo> modelos = this.modeloRepository.findByAtivos();
        return ResponseEntity.ok(modelos);
    }

    // POST
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try {
            this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok(modelo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getStackTrace());
        }
    }

//  PUT
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Modelo modelo)
    {
        try {
            this.modeloService.editar(id, modelo);
            return ResponseEntity.ok(modelo);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }


    //    DELETE -> Se o modelo estiver vinculado a um veiculo então ativo = false senão delete
    @DeleteMapping
    public ResponseEntity <?> deletar (@RequestParam ("id") final Long id) {

        try {
            this.modeloService.deletar(id);
            return ResponseEntity.ok("Modelo setado como inativo");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }

}
