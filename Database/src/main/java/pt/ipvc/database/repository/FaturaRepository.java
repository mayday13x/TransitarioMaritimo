package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.FaturaEntity;

@Repository
public interface FaturaRepository extends JpaRepository<FaturaEntity, Integer> {
}
