package pt.ipvc.database.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CargaEntity;

@Repository
public interface CargaRepository extends JpaRepository<CargaEntity, Integer> {
}
