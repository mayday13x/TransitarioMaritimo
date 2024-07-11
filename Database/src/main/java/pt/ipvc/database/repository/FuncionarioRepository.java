package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.FuncionarioEntity;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {

    @Query("SELECT f FROM FuncionarioEntity f WHERE f.id = :id")
    FuncionarioEntity findByidLike(@Param("id") String id);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FuncionarioEntity f WHERE f.utilizador = :username AND f.password = :password")
    boolean existsByEmailAndPassword(@Param("username") String username, @Param("password") String password);
}
