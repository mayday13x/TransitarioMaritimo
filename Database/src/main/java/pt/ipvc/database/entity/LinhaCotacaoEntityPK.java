package pt.ipvc.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class LinhaCotacaoEntityPK implements Serializable {
    private int idCotacao;
    private int idServico;

    @Column(name = "id_cotacao", nullable = false)
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdCotacao() {
        return idCotacao;
    }

    public void setIdCotacao(int idCotacao) {
        this.idCotacao = idCotacao;
    }

    @Column(name = "id_servico", nullable = false)
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        LinhaCotacaoEntityPK that = (LinhaCotacaoEntityPK) o;

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
}
