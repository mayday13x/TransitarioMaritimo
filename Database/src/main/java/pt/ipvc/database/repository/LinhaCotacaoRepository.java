package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ipvc.database.entity.LinhaCotacaoEntity;

@Repository
public interface LinhaCotacaoRepository extends JpaRepository<LinhaCotacaoEntity, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CargaEntity c WHERE c.idCotacao = :cotacaoId")
    void deleteByIdCotacao(@Param("cotacaoId") int cotacaoId);
}
