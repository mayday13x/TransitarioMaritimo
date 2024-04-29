package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CotacaoEntity;

@Repository
public interface CotacaoRepository extends JpaRepository<CotacaoEntity,Integer> {
}
