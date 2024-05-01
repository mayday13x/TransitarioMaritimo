package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.TipoContentorEntity;

@Repository
public interface TipoContentorRepository extends JpaRepository<TipoContentorEntity, Integer> {

    @Query("SELECT tc FROM TipoContentorEntity tc WHERE tc.descricao LIKE :descricao")
    TipoContentorEntity findByNameLike(@Param("descricao") String descricao);
}
