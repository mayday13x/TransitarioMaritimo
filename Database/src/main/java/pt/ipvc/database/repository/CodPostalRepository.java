package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import pt.ipvc.database.entity.CodPostalEntity;

@Repository
public interface CodPostalRepository extends JpaRepository<CodPostalEntity,Integer> {

    @Query("SELECT cp FROM CodPostalEntity cp WHERE cp.localidade LIKE :localidade")
    CodPostalEntity findByNameLike(@Param("localidade") String localidade);



}
