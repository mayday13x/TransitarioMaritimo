package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.LinhaCotacaoEntity;
import pt.ipvc.database.entity.ServicoEntity;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity,Integer> {

    // selecionar linhaCotacao para um determinado servico e cotacao

    @Query("SELECT lc FROM LinhaCotacaoEntity lc WHERE lc.idServico = :idServico AND lc.idCotacao = :idCotacao")
    LinhaCotacaoEntity findLinhaCotacaoByIdServicoAndCotacao(@Param("idServico") Integer idServico, @Param("idCotacao") Integer idCotacao);

}
