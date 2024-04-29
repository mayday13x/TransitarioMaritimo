package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ReciboEntity;

@Repository
public interface ReciboRepository extends JpaRepository<ReciboEntity, Integer> {
}
