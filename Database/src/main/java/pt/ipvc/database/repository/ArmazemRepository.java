package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ArmazemEntity;

@Repository
public interface ArmazemRepository extends JpaRepository<ArmazemEntity,Integer> {

}
