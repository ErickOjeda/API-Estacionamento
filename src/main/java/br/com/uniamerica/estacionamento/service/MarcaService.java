package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Marca marca){

        Assert.isTrue(marca.getNome() != null, "Nome não informado");

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Marca marca){

        Assert.isTrue(this.marcaRepository.existsById(id), "Não foi possivel identificar o registro informado");
        Assert.isTrue(marca.getId().equals(id), "Id enviado nao coincide com id no corpo da requisicao");

        Assert.isTrue(marca.getNome() != null, "Nome não informado");

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id){

        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        Assert.isTrue(marcaBanco != null, "Registro não encontrado");

        List<Modelo> modelos = this.modeloRepository.findModelosByMarca(marcaBanco);

        if(modelos.isEmpty()){
            this.marcaRepository.delete(marcaBanco);
        }
        else{
            marcaBanco.setAtivo(false);
            this.marcaRepository.save(marcaBanco);
        }
    }

}
