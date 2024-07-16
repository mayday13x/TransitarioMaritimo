package pt.ipvc.database.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.CargaEntity;

import java.util.List;

@Repository
public interface CargaRepository extends JpaRepository<CargaEntity, Integer> {


    @Query("SELECT c FROM CargaEntity c join ArmazemEntity a on c.idArmazem = a.id  where :armazemId = c.idArmazem order by c.id")
    List<CargaEntity> findByArmazemID(@Param("armazemId") int armazemId);

    // listar cargas de um determinado armazem em que o id_contentor é null
    @Query("SELECT c FROM CargaEntity c WHERE c.idArmazem = :armazemId AND c.idContentor IS NULL")
    List<CargaEntity> findByIdArmazemAndContentorNull(@Param("armazemId") int armazemId);


    @Query("SELECT ca FROM CargaEntity ca join ContentorEntity c on c.cin = ca.idContentor  where :contentorCin = c.cin order by ca.id")
    List<CargaEntity> findByContentorCin(@Param("contentorCin") int contentorCin);

    @Query("SELECT cp.id FROM TipoCargaEntity cp WHERE cp.descricao LIKE :descricao")
    Integer findByNameLike(@Param("descricao") String descricao);

    @Query("SELECT c FROM CargaEntity c where :armazemId = c.id")
    CargaEntity findByID(@Param("armazemId") int armazemId);


    @Query("SELECT c FROM CargaEntity c join CotacaoEntity co on c.idCotacao = co.id  where co.id = :cotacaoId")
    List<CargaEntity> findByIdCotacao(@Param("cotacaoId") int cotacaoId);

    @Modifying
    @Query("UPDATE CargaEntity c SET c.idArmazem = null WHERE c.idContentor = :contentorId")
    void updateArmazemIdByContentorId(int contentorId);

    // selecionar as cargas que não têm id_armazem e que o estado_reserva = 'Pago'
    @Query("SELECT c FROM CargaEntity c JOIN ReservaEntity r ON c.idReserva = r.id JOIN EstadoReservaEntity er " +
            "ON r.idEstadoReserva = er.id WHERE c.idArmazem IS NULL AND er.descricao LIKE 'Pago'")
    List<CargaEntity> findByIdArmazemNullAndReservaPago();





}
