package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ArmazemEntity;
import pt.ipvc.database.entity.TipoCargaEntity;

@Repository
public interface ArmazemRepository extends JpaRepository<ArmazemEntity,Integer> {

    @Query("SELECT a FROM ArmazemEntity a WHERE a.id = :id")
    ArmazemEntity findByIdLike(@Param("id") Integer id);
}
