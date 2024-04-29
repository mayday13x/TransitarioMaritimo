package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.TransportemaritimoEntity;

@Repository
public interface TransportemaritimoRepository extends JpaRepository<TransportemaritimoEntity, Integer> {
}
