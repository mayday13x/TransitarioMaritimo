package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "linha_cotacao", schema = "public", catalog = "transitario_maritimo")
@IdClass(LinhaCotacaoEntityPK.class)
public class LinhaCotacaoEntity {
    private int idCotacao;
    private int idServico;
    private CotacaoEntity cotacaoByIdCotacao;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cotacao", nullable = false)
    public int getIdCotacao() {
        return idCotacao;
    }

    public void setIdCotacao(int idCotacao) {
        this.idCotacao = idCotacao;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_servico", nullable = false)
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaCotacaoEntity that = (LinhaCotacaoEntity) o;

        if (idCotacao != that.idCotacao) return false;
        if (idServico != that.idServico) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCotacao;
        result = 31 * result + idServico;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_cotacao", referencedColumnName = "id", nullable = false)
    public CotacaoEntity getCotacaoByIdCotacao() {
        return cotacaoByIdCotacao;
    }

    public void setCotacaoByIdCotacao(CotacaoEntity cotacaoByIdCotacao) {
        this.cotacaoByIdCotacao = cotacaoByIdCotacao;
    }
}
