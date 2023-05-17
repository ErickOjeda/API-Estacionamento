package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    @Query("from Condutor where ativo = true")
    public List<Condutor> findAtivos();

    @Query("from Condutor where cpf = :cpf")
    public Condutor findCpf(@Param("cpf") final String cpf);

    @Query("from Condutor where cpf = :cpf AND id != :id")
    public Condutor findCpfPut(@Param("cpf") final String cpf, @Param("id") final Long id);

    @Query("from Condutor where telefone = :telefone")
    public Condutor findTelefone(@Param("telefone") final String telefone);

    @Query("from Condutor where telefone = :telefone AND id != :id")
    public Condutor findTelefonePut(@Param("telefone") final String tel, @Param("id") final Long id);


}
