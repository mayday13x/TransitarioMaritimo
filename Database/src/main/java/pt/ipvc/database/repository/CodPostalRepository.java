package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CodPostalEntity;

@Repository
public interface CodPostalRepository extends JpaRepository<CodPostalEntity,Integer> {
}
