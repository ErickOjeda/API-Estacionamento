package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    @Query("from Movimentacao where saida = null")
    public  List<Movimentacao> findAbertas();

    @Query("from Movimentacao where condutor = :condutor")
    public List<Movimentacao> findMovimentacoesByCondutor(@Param("condutor") final Condutor condutor);

    @Query("from Movimentacao where veiculo = :veiculo")
    public List<Movimentacao> findMovimentacoesByVeiculo(@Param("veiculo") final Veiculo veiculo);


}
