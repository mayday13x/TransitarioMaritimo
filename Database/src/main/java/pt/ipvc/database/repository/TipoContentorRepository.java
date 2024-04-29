package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.TipoContentorEntity;

@Repository
public interface TipoContentorRepository extends JpaRepository<TipoContentorEntity, Integer> {
}
