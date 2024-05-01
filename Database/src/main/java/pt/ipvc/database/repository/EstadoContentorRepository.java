package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.EstadoContentorEntity;

@Repository
public interface EstadoContentorRepository extends JpaRepository<EstadoContentorEntity, Integer> {

    @Query("SELECT ec FROM EstadoContentorEntity ec WHERE ec.descricao LIKE :descricao")
    EstadoContentorEntity findByNameLike(@Param("descricao") String descricao);
}
