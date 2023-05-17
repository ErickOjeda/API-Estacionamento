package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    @Query("from Modelo where ativo = true")
    public  List<Modelo> findByAtivos();

    @Query("from Modelo where marca = :marca")
    public List<Modelo> findModelosByMarca(@Param("marca") final Marca marca);

}
