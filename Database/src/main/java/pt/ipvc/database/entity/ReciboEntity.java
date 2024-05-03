package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "recibo", schema = "public", catalog = "transitario_maritimo")
public class ReciboEntity {
    private int id;
    private Date dataEmissao;
    private Integer idFatura;
    private FaturaEntity faturaByIdFatura;

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
    @Column(name = "data_emissao", nullable = true)
    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @Basic
    @Column(name = "id_fatura", nullable = true, insertable=false, updatable=false)
    public Integer getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(Integer idFatura) {
        this.idFatura = idFatura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReciboEntity that = (ReciboEntity) o;

        if (id != that.id) return false;
        if (dataEmissao != null ? !dataEmissao.equals(that.dataEmissao) : that.dataEmissao != null) return false;
        if (idFatura != null ? !idFatura.equals(that.idFatura) : that.idFatura != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dataEmissao != null ? dataEmissao.hashCode() : 0);
        result = 31 * result + (idFatura != null ? idFatura.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_fatura", referencedColumnName = "id")
    public FaturaEntity getFaturaByIdFatura() {
        return faturaByIdFatura;
    }

    public void setFaturaByIdFatura(FaturaEntity faturaByIdFatura) {
        this.faturaByIdFatura = faturaByIdFatura;
    }
}
