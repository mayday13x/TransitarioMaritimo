package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "armazem", schema = "public", catalog = "transitario_maritimo")
public class ArmazemEntity {
    private int id;
    private Double capacidadeMax;
    private Collection<CargaEntity> cargasById;

    public ArmazemEntity(){}

    public ArmazemEntity(Double capacidadeMax){
        this.capacidadeMax = capacidadeMax;
    }

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
    @Column(name = "capacidade_max", nullable = true, precision = 0)
    public Double getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(Double capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArmazemEntity that = (ArmazemEntity) o;

        if (id != that.id) return false;
        return Objects.equals(capacidadeMax, that.capacidadeMax);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (capacidadeMax != null ? capacidadeMax.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "armazemByIdArmazem")
    public Collection<CargaEntity> getCargasById() {
        return cargasById;
    }

    public void setCargasById(Collection<CargaEntity> cargasById) {
        this.cargasById = cargasById;
    }
}
