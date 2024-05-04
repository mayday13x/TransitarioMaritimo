package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.TipoCargaEntity;

@Repository
public interface TipoCargaRepository extends JpaRepository<TipoCargaEntity, Integer> {

    @Query("SELECT tp FROM TipoCargaEntity tp WHERE tp.descricao LIKE :descricao")
    TipoCargaEntity findByDescLike(@Param("descricao") String descricao);
}
