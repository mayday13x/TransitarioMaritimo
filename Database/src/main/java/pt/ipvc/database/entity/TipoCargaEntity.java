package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "tipo_carga", schema = "public", catalog = "transitario_maritimo")
public class TipoCargaEntity {
    private int id;
    private String descricao;
    private Collection<CargaEntity> cargasById;

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
    @Column(name = "descricao", nullable = true, length = 255)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoCargaEntity that = (TipoCargaEntity) o;

        if (id != that.id) return false;
        if (descricao != null ? !descricao.equals(that.descricao) : that.descricao != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tipoCargaByIdTipoCarga")
    public Collection<CargaEntity> getCargasById() {
        return cargasById;
    }

    public void setCargasById(Collection<CargaEntity> cargasById) {
        this.cargasById = cargasById;
    }
}
