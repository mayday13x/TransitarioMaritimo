package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoCargaEntity;

@Repository
public interface EstadoCargaRepository extends JpaRepository<EstadoCargaEntity,Integer> {
}
