package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoCargaEntity;

@Repository
public interface EstadoCargaRepository extends JpaRepository<EstadoCargaEntity,Integer> {

    @Query("SELECT e FROM EstadoCargaEntity e WHERE e.id = :id")
    EstadoCargaEntity findByIdLike(@Param("id") Integer id);
}
