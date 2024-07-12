package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.FuncionarioEntity;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {

    @Query("SELECT f FROM FuncionarioEntity f WHERE f.id = :id")
    FuncionarioEntity findByidLike(@Param("id") String id);

    @Query("SELECT f FROM FuncionarioEntity f WHERE f.utilizador = :username AND f.password = :password")
    FuncionarioEntity findByEmailAndPassword(@Param("username") String username, @Param("password") String password);

    // selecionar os funcionarios cujo id_tipo_funcionario corresponde à descricao "Transporte Maritimo" da
    // tabela tipo_funcionario

    @Query("SELECT f FROM FuncionarioEntity f JOIN TipoFuncionarioEntity tf ON f.idTipoFuncionario = tf.id WHERE tf.descricao = 'Transporte Maritimo'")
    List<FuncionarioEntity> findTransporteMaritimo();


}
