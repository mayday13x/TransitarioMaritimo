package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoContentorEntity;

@Repository
public interface EstadoContentorRepository extends JpaRepository<EstadoContentorEntity, Integer> {
}
