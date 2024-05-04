package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@jakarta.persistence.Table(name = "fatura", schema = "public", catalog = "transitario_maritimo")
public class FaturaEntity {
    private int id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Integer idEstadoFatura;

    @Basic
    @Column(name = "id_estado_fatura", nullable = true)
    public Integer getIdEstadoFatura() {
        return idEstadoFatura;
    }

    public void setIdEstadoFatura(Integer idEstadoFatura) {
        this.idEstadoFatura = idEstadoFatura;
    }

    private Date dataLimite;

    @Basic
    @Column(name = "data_limite", nullable = true)
    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    private Date dataEmissao;

    @Basic
    @Column(name = "data_emissao", nullable = true)
    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    private Integer idCotacao;

    @Basic
    @Column(name = "id_cotacao", nullable = true)
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
}
