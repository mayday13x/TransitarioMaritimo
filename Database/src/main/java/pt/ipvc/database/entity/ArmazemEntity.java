package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "armazem", schema = "public", catalog = "transitario_maritimo")
public class ArmazemEntity {
    private Integer id;
    private Double capacidadeMax;
    private String descricao;
    private Collection<CargaEntity> cargasById;
    private Collection<FuncionarioEntity> funcionariosById;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "capacidade_max", nullable = false, precision = 0)
    public Double getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(double capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    @Basic
    @Column(name = "descricao", nullable = true, length = -1)
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

        ArmazemEntity that = (ArmazemEntity) o;

        if (id != that.id) return false;
        if (Double.compare(capacidadeMax, that.capacidadeMax) != 0) return false;
        if (descricao != null ? !descricao.equals(that.descricao) : that.descricao != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(capacidadeMax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "armazemByIdArmazem")
    public Collection<CargaEntity> getCargasById() {
        return cargasById;
    }

    public void setCargasById(Collection<CargaEntity> cargasById) {
        this.cargasById = cargasById;
    }

    @OneToMany(mappedBy = "armazemByIdArmazem")
    public Collection<FuncionarioEntity> getFuncionariosById() {
        return funcionariosById;
    }

    public void setFuncionariosById(Collection<FuncionarioEntity> funcionariosById) {
        this.funcionariosById = funcionariosById;
    }
}
