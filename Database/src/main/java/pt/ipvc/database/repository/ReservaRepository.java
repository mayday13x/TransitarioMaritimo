package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ReservaEntity;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity,Integer> {

    @Query("SELECT r FROM ReservaEntity r WHERE r.id = :id")
    ReservaEntity findByIdLike(@Param("id") Integer id);

    @Query("SELECT r FROM ReservaEntity r WHERE r.idCliente = :id")
    List<ReservaEntity> findByIdClienteLike(@Param("id") Integer id);

    @Query("SELECT r FROM ReservaEntity r WHERE r.idEstadoReserva = 2")
    List<ReservaEntity> findByIdEstadoReserva();
}
