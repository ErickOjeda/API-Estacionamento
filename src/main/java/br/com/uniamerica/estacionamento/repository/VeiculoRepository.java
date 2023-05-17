package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VeiculoRepository  extends JpaRepository<Veiculo, Long> {
    @Query("from Veiculo where ativo = true")
    public List<Veiculo> findByAtivos();

    @Query("from Veiculo where modelo = :modelo")
    public List<Veiculo> findVeiculosByModelo(@Param("modelo") final Modelo modelo);

    @Query("from Veiculo where placa = :placa")
    public List<Veiculo> findVeiculosByPlaca(@Param("placa") final String placa);

}
