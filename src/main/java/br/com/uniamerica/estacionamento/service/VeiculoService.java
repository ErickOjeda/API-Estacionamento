package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){

        Assert.isTrue(veiculo.getPlaca() != null, "Placa não informada");
        Assert.isTrue(veiculo.getCor() != null, "Cor não informada");
        Assert.isTrue(veiculo.getModelo() != null, "Modelo não informada");
        Assert.isTrue(veiculo.getTipo() != null, "Tipo não informado");
        Assert.isTrue(veiculo.getAno() != null, "Ano não informado");

        final Long id_modelo = veiculo.getModelo().getId();
        final Modelo modelo = this.modeloRepository.findById(id_modelo).orElse(null);
        Assert.isTrue(modelo != null, "Modelo inválido");

        String validaPlaca = "^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$";

        Assert.isTrue(veiculo.getPlaca().matches(validaPlaca), "Placa inválida (ABC1A34)");

        final List<Veiculo> veiculoPlaca = this.veiculoRepository.findVeiculosByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoPlaca.isEmpty(), "Placa já cadastrada");

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Veiculo veiculo){

        Assert.isTrue(this.veiculoRepository.existsById(id), "Não foi possivel identificar o registro informado");
        Assert.isTrue(veiculo.getId().equals(id), "Não foi possivel identificar o registro informado");

        Assert.isTrue(veiculo.getPlaca() != null, "Placa não informada");
        Assert.isTrue(veiculo.getCor() != null, "Cor não informada");
        Assert.isTrue(veiculo.getModelo() != null, "Modelo não informada");
        Assert.isTrue(veiculo.getTipo() != null, "Tipo não informado");
        Assert.isTrue(veiculo.getAno() != null, "Ano não informado");

        final Long id_modelo = veiculo.getModelo().getId();
        final Modelo modelo = this.modeloRepository.findById(id_modelo).orElse(null);
        Assert.isTrue(modelo != null, "Modelo inválido");

        String validaPlaca = "^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$";

        Assert.isTrue(veiculo.getPlaca().matches(validaPlaca), "Placa inválida (ABC-1234)");

        final List<Veiculo> veiculoPlaca = this.veiculoRepository.findVeiculosByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoPlaca.isEmpty(), "Placa já cadastrada");
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id){

        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        Assert.isTrue (veiculoBanco != null, "Registro não encontrado");

        List<Movimentacao> movs = this.movimentacaoRepository.findMovimentacoesByVeiculo(veiculoBanco);

        if(movs.isEmpty()){
            this.veiculoRepository.delete(veiculoBanco);
        }
        else{
            veiculoBanco.setAtivo(false);
            this.veiculoRepository.save(veiculoBanco);
        }
    }

}
