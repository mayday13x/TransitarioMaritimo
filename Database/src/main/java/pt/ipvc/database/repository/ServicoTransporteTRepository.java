package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ServicoTransporteTEntity;

@Repository
public interface ServicoTransporteTRepository extends JpaRepository<ServicoTransporteTEntity, Integer> {
}
