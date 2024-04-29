package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoReservaEntity;

@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReservaEntity,Integer> {
}
