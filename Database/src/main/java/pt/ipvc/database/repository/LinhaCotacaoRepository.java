package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.LinhaCotacaoEntity;

@Repository
public interface LinhaCotacaoRepository extends JpaRepository<LinhaCotacaoEntity, Integer> {
}
