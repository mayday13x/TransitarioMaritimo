package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoReservaEntity;

@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReservaEntity,Integer> {

    @Query("SELECT es FROM EstadoReservaEntity es WHERE es.descricao LIKE :descricao")
    EstadoReservaEntity findByDescricaoLike(@Param("descricao") String descricao);
}
