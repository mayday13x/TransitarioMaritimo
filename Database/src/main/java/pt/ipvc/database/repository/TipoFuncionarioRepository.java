package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.TipoFuncionarioEntity;

@Repository
public interface TipoFuncionarioRepository extends JpaRepository<TipoFuncionarioEntity, Integer> {

    @Query("SELECT tp FROM TipoFuncionarioEntity tp WHERE tp.descricao LIKE :descricao")
    TipoFuncionarioEntity findByDescLike(@Param("descricao") String descricao);
}
