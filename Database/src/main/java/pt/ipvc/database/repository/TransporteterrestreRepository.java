package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.TransporteterrestreEntity;

@Repository
public interface TransporteterrestreRepository extends JpaRepository<TransporteterrestreEntity, Integer> {
}
