package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Condutor condutor) {

        Assert.isTrue(condutor.getCpf() != null, "CPF não informado");
        Assert.isTrue(condutor.getNome() != null, "Nome não informado");
        Assert.isTrue(condutor.getTelefone() != null, "Telefone não informado");

        final Condutor condutorCPF = this.condutorRepository.findCpf(condutor.getCpf());
        Assert.isTrue(condutorCPF ==  null, "CPF já cadastrado");

        String validaCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(validaCpf), "CPF inválido (000.000.000-00)");

        String validaTelefone = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";
        Assert.isTrue(!condutor.getTelefone().matches(validaTelefone), "Telefone inválido (+00 123 12345-1234)");

        final Condutor condutorTelefone = this.condutorRepository.findTelefone(condutor.getTelefone());
        Assert.isTrue(condutorTelefone == null, "Telefone já cadastrado");

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id,final Condutor condutor){

        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

        Assert.isTrue(condutorBanco != null, "Não foi possivel identificar o registro informado");
        Assert.isTrue(condutor.getId().equals(id), "Não foi possivel identificar o registro informado");

        Assert.isTrue(condutor.getCpf() != null, "CPF não informado");
        Assert.isTrue(condutor.getNome() != null, "Nome não informado");
        Assert.isTrue(condutor.getTelefone() != null, "Telefone não informado");

        final Condutor condutorCPF = this.condutorRepository.findCpfPut(condutor.getCpf(), id);
        Assert.isTrue(condutorCPF ==  null, "CPF já cadastrado");

        String validaCpf = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        Assert.isTrue(condutor.getCpf().matches(validaCpf), "CPF inválido (000.000.000-00)");

        String validaTelefone = "\\+\\d{2}\\(\\d{3}\\)\\d{5}-\\d{4}";
        Assert.isTrue(!condutor.getTelefone().matches(validaTelefone), "Telefone inválido (+00 123 12345-1234)");

        final Condutor condutorTelefone = this.condutorRepository.findTelefonePut(condutor.getTelefone(), id);
        Assert.isTrue(condutorTelefone == null, "Telefone já cadastrado");

        this.condutorRepository.save(condutor);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id) {

        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        Assert.isTrue(condutorBanco != null, "Registro não encontrado");

        List<Movimentacao> movs = this.movimentacaoRepository.findMovimentacoesByCondutor(condutorBanco);

        if(movs.isEmpty()){
            this.condutorRepository.delete(condutorBanco);
        }
        else{
            condutorBanco.setAtivo(false);
            this.condutorRepository.save(condutorBanco);
        }

    }

}
