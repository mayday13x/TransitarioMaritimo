package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.EstadoCotacaoEntity;

@Repository
public interface EstadoCotacaoRepository extends JpaRepository<EstadoCotacaoEntity, Integer> {
}
