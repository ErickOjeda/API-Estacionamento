package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo){

        Assert.isTrue(modelo.getNome() != null, "Nome não informado");
        Assert.isTrue(modelo.getMarca() != null, "Marca não informada");

        final Long id_marca = modelo.getMarca().getId();
        final Marca marca = this.marcaRepository.findById(id_marca).orElse(null);
        Assert.isTrue(marca != null, "Marca inválida");


        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Modelo modelo){

        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

        Assert.isTrue((modeloBanco != null), "Não foi possivel identificar o registro informado");
        Assert.isTrue(modelo.getId().equals(id), "Não foi possivel identificar o registro informado");

        Assert.isTrue(modelo.getNome() != null, "Nome não informado");
        Assert.isTrue(modelo.getMarca() != null, "Marca não informada");

        final Long id_marca = modelo.getMarca().getId();
        final Marca marca = this.marcaRepository.findById(id_marca).orElse(null);
        Assert.isTrue(marca != null, "Marca inválida");

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id){

        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        Assert.isTrue(modeloBanco != null, "Registro não encontrado");

        List<Veiculo> veiculos = this.veiculoRepository.findVeiculosByModelo(modeloBanco);

        if(veiculos.isEmpty()){
            this.modeloRepository.delete(modeloBanco);
        }
        else{
            modeloBanco.setAtivo(false);
            this.modeloRepository.save(modeloBanco);
        }
    }

}
