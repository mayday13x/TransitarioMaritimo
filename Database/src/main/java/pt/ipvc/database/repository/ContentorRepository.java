package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ContentorEntity;

import java.util.List;

@Repository
public interface ContentorRepository extends JpaRepository<ContentorEntity,Integer> {

    @Query("SELECT a FROM ContentorEntity a WHERE a.cin = :id")
    ContentorEntity findByIdLike(@Param("id") Integer id);

    @Query("SELECT a FROM ContentorEntity a WHERE a.idArmazem = :armazemId")
    List<ContentorEntity> findByIdArmazemLike(@Param("armazemId") Integer armazemId);

    // selecionar contentores de um armazem
    @Query("SELECT c FROM ContentorEntity c JOIN ArmazemEntity a ON c.idArmazem = a.id WHERE a.id = :armazemId")
    List<ContentorEntity> findByIdArmazem(@Param("armazemId") Integer armazemId);

}
