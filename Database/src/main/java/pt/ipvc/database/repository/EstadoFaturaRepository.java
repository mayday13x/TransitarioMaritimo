package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoFaturaEntity;

@Repository
public interface EstadoFaturaRepository extends JpaRepository<EstadoFaturaEntity,Integer> {
}
