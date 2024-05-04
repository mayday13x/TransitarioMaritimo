package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ClienteEntity;
import pt.ipvc.database.entity.CodPostalEntity;
import pt.ipvc.database.entity.FornecedorEntity;

@Repository
public interface FornecedorRepository  extends JpaRepository<FornecedorEntity, Integer> {
    @Query("SELECT id_forn FROM FornecedorEntity id_forn WHERE id_forn.nome LIKE :nome")
    FornecedorEntity findByNameLike(@Param("nome") String nome);

}
