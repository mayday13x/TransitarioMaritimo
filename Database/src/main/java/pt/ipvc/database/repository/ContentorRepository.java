package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ContentorEntity;

@Repository
public interface ContentorRepository extends JpaRepository<ContentorEntity,Integer> {

    @Query("SELECT a FROM ContentorEntity a WHERE a.cin = :id")
    ContentorEntity findByIdLike(@Param("id") Integer id);
}
