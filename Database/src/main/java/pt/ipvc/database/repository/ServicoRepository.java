package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ServicoEntity;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity,Integer> {
}
