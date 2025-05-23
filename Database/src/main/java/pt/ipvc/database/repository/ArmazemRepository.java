package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.ArmazemEntity;

import java.util.List;

@Repository
public interface ArmazemRepository extends JpaRepository<ArmazemEntity,Integer> {

    @Query("SELECT a FROM ArmazemEntity a WHERE a.id = :id")
    ArmazemEntity findByIdLike(@Param("id") Integer id);

    //somar os volumes das cargas presentes no armazem
    @Query("SELECT SUM(c.volume) FROM CargaEntity c WHERE c.idArmazem = :idArmazem")
    Double sumVolumes(@Param("idArmazem") Integer idArmazem);

    //contar quantas cargas estao em armazem
    @Query("SELECT COUNT(c.id) FROM CargaEntity c WHERE c.idArmazem = :idArmazem")
    Long countCargasInArmazem(@Param("idArmazem") Integer idArmazem);


    @Query("SELECT a FROM ArmazemEntity a LEFT JOIN FuncionarioEntity f ON a.id = f.idArmazem WHERE f.id IS NULL")
    List<ArmazemEntity> findArmazensSemFuncionario();
}
