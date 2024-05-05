package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoCotacaoEntity;
import pt.ipvc.database.entity.EstadoReservaEntity;

@Repository
public interface EstadoCotacaoRepository extends JpaRepository<EstadoCotacaoEntity, Integer> {

    @Query("SELECT ec FROM EstadoCotacaoEntity ec WHERE ec.descricao LIKE :descricao")
    EstadoCotacaoEntity findByDescricaoLike(@Param("descricao") String descricao);
}
