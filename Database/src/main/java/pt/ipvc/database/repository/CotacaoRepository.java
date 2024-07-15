package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CotacaoEntity;
import pt.ipvc.database.entity.ServicoEntity;

import java.util.List;

@Repository
public interface CotacaoRepository extends JpaRepository<CotacaoEntity,Integer> {

    @Query("SELECT c FROM CotacaoEntity c WHERE c.id = :id")
    CotacaoEntity findByIdLike(@Param("id") Integer id);

    @Query("SELECT c FROM CotacaoEntity c WHERE c.idCliente = :id")
    List<CotacaoEntity> findByIdClienteLike(@Param("id") Integer id);


    @Query("SELECT s FROM ServicoEntity s join LinhaCotacaoEntity lc on lc.idServico = s.id " +
            "join CotacaoEntity  c on lc.idCotacao = c .id  where c.idCliente = :id")
    List<ServicoEntity> findByIdClienteServico(@Param("id") Integer id);

    //selecionar os serviços com um determinado id_cotacao
    @Query("SELECT s FROM ServicoEntity s join LinhaCotacaoEntity lc on lc.idServico = s.id " +
            "join CotacaoEntity  c on lc.idCotacao = :idCotacao")
    List<ServicoEntity> findByIdCotacao(@Param("idCotacao") Integer idCotacao);

    //@Query("SELECT c FROM CotacaoEntity c WHERE c.idEstadoCotacao = 2 ")
    //List<CotacaoEntity> findByEstadoConfirmado();

    @Query("SELECT c FROM CotacaoEntity c JOIN EstadoCotacaoEntity ec ON c.idEstadoCotacao = ec.id WHERE ec.descricao LIKE 'Confirmado'")
    List<CotacaoEntity> findByEstadoConfirmado();

    //mostrar cotaoes em que o id_estado_cotacao é 2
    @Query("SELECT c FROM CotacaoEntity c WHERE c.idEstadoCotacao = 2 AND NOT EXISTS (SELECT r FROM ReservaEntity r WHERE r.cotacaoByIdCotacao.id = c.id)")
    List<CotacaoEntity> findByEstadoConfirmadoSemReserva();

}
