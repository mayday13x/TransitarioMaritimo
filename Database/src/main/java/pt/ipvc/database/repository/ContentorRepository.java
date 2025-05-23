package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CargaEntity;
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


    // selecionar contentores de um armazem em que o id_estado_contentor não é "Contentor Fora"
    @Query("SELECT c FROM ContentorEntity c WHERE c.idArmazem = :armazemId AND c.idEstadoContentor!= 3")
    List<ContentorEntity> findByIdArmazemNotFora(@Param("armazemId") Integer armazemId);

    // somar os volumes das cargas de um contentor
    @Query("SELECT SUM(c.volume) FROM CargaEntity c WHERE c.idContentor = :idContentor")
    Double sumVolumes(@Param("idContentor") Integer idContentor);

    // somar os pesos das carga de um contentor
    @Query("SELECT SUM(c.peso) FROM CargaEntity c WHERE c.idContentor = :idContentor")
    Double sumPesos(@Param("idContentor") Integer idContentor);

    //selecionar as cargas de um contentor
    @Query("SELECT c FROM CargaEntity c WHERE c.idContentor = :idContentor")
    List<CargaEntity> findByIdContentor(@Param("idContentor") Integer idContentor);

    // contar as cargas de um contentor
    @Query("SELECT COUNT(c) FROM CargaEntity c WHERE c.idContentor = :idContentor")
    Long countByIdContentor(@Param("idContentor") Integer idContentor);

    // selecionar contentores de um armazem
    @Query("SELECT c FROM ContentorEntity c where c.idEstadoContentor = 3")
    List<ContentorEntity> findByEstado();

<<<<<<< HEAD
    // selecionar contentores cujo o id_estado_contentor não corresponda a "Em consolidação"
    @Query("SELECT c FROM ContentorEntity c WHERE c.idEstadoContentor!= 2")
    List<ContentorEntity> findByNotEmConsolidacao();
=======
    // selecionar contentores de um armazem
    @Query("SELECT c FROM ContentorEntity c where c.idEstadoContentor = 1 or c.idEstadoContentor = 3")
    List<ContentorEntity> findByEstadoProntoAndFora();


>>>>>>> 76cbadc1b1e0bcf21b572cd74962038565c63bd1

}
