package pt.ipvc.database.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CargaEntity;
import pt.ipvc.database.entity.CodPostalEntity;

import java.util.List;

@Repository
public interface CargaRepository extends JpaRepository<CargaEntity, Integer> {


    @Query("SELECT c FROM CargaEntity c join ArmazemEntity a on c.idArmazem = a.id  where :armazemId = c.idArmazem order by c.id")
    List<CargaEntity> findByArmazemID(@Param("armazemId") int armazemId);

    @Query("SELECT ca FROM CargaEntity ca join ContentorEntity c on c.cin = ca.idContentor  where :contentorCin = c.cin order by ca.id")
    List<CargaEntity> findByContentorCin(@Param("contentorCin") int contentorCin);

    @Query("SELECT cp.id FROM TipoCargaEntity cp WHERE cp.descricao LIKE :descricao")
    Integer findByNameLike(@Param("descricao") String descricao);
}
