package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("SELECT c FROM ClienteEntity c WHERE c.id = :id")
    ClienteEntity findByidLike(@Param("id") String id);

    @Query("SELECT c FROM ClienteEntity c WHERE c.utilizador = :username AND c.password = :password")
    ClienteEntity findByEmailAndPassword(@Param("username") String username, @Param("password") String password);
}
