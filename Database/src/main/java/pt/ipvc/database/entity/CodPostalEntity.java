package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "cod_postal", schema = "public", catalog = "transitario_maritimo")
public class CodPostalEntity {
    private int idCodPostal;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id_cod_postal", nullable = false)
    public int getIdCodPostal() {
        return idCodPostal;
    }

    public void setIdCodPostal(int idCodPostal) {
        this.idCodPostal = idCodPostal;
    }

    private String localidade;

    @Basic
    @Column(name = "localidade", nullable = true, length = 255)
    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodPostalEntity that = (CodPostalEntity) o;

        if (idCodPostal != that.idCodPostal) return false;
        if (localidade != null ? !localidade.equals(that.localidade) : that.localidade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCodPostal;
        result = 31 * result + (localidade != null ? localidade.hashCode() : 0);
        return result;
    }
}
