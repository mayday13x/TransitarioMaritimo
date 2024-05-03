package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fatura", schema = "public", catalog = "transitario_maritimo")
public class FaturaEntity {
    private int id;
    private Integer idEstadoFatura;
    private Date dataLimite;
    private Date dataEmissao;
    private Integer idCotacao;
    private EstadoFaturaEntity estadoFaturaByIdEstadoFatura;
    private CotacaoEntity cotacaoByIdCotacao;
    private Collection<ReciboEntity> recibosById;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_estado_fatura", nullable = true, insertable=false, updatable=false)
    public Integer getIdEstadoFatura() {
        return idEstadoFatura;
    }

    public void setIdEstadoFatura(Integer idEstadoFatura) {
        this.idEstadoFatura = idEstadoFatura;
    }

    @Basic
    @Column(name = "data_limite", nullable = true)
    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    @Basic
    @Column(name = "data_emissao", nullable = true)
    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @Basic
    @Column(name = "id_cotacao", nullable = true, insertable=false, updatable=false)
    public Integer getIdCotacao() {
        return idCotacao;
    }

    public void setIdCotacao(Integer idCotacao) {
        this.idCotacao = idCotacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FaturaEntity that = (FaturaEntity) o;

        if (id != that.id) return false;
        if (idEstadoFatura != null ? !idEstadoFatura.equals(that.idEstadoFatura) : that.idEstadoFatura != null)
            return false;
        if (dataLimite != null ? !dataLimite.equals(that.dataLimite) : that.dataLimite != null) return false;
        if (dataEmissao != null ? !dataEmissao.equals(that.dataEmissao) : that.dataEmissao != null) return false;
        if (idCotacao != null ? !idCotacao.equals(that.idCotacao) : that.idCotacao != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idEstadoFatura != null ? idEstadoFatura.hashCode() : 0);
        result = 31 * result + (dataLimite != null ? dataLimite.hashCode() : 0);
        result = 31 * result + (dataEmissao != null ? dataEmissao.hashCode() : 0);
        result = 31 * result + (idCotacao != null ? idCotacao.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado_fatura", referencedColumnName = "id")
    public EstadoFaturaEntity getEstadoFaturaByIdEstadoFatura() {
        return estadoFaturaByIdEstadoFatura;
    }

    public void setEstadoFaturaByIdEstadoFatura(EstadoFaturaEntity estadoFaturaByIdEstadoFatura) {
        this.estadoFaturaByIdEstadoFatura = estadoFaturaByIdEstadoFatura;
    }

    @ManyToOne
    @JoinColumn(name = "id_cotacao", referencedColumnName = "id")
    public CotacaoEntity getCotacaoByIdCotacao() {
        return cotacaoByIdCotacao;
    }

    public void setCotacaoByIdCotacao(CotacaoEntity cotacaoByIdCotacao) {
        this.cotacaoByIdCotacao = cotacaoByIdCotacao;
    }

    @OneToMany(mappedBy = "faturaByIdFatura")
    public Collection<ReciboEntity> getRecibosById() {
        return recibosById;
    }

    public void setRecibosById(Collection<ReciboEntity> recibosById) {
        this.recibosById = recibosById;
    }
}
