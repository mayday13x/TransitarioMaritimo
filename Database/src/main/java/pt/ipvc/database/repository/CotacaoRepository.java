package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CotacaoEntity;

@Repository
public interface CotacaoRepository extends JpaRepository<CotacaoEntity,Integer> {

    @Query("SELECT c FROM CotacaoEntity c WHERE c.id = :id")
    CotacaoEntity findByIdLike(@Param("id") Integer id);
}
