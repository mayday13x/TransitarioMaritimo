package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "armazem", schema = "public", catalog = "transitario_maritimo")
public class ArmazemEntity {
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

    private double capacidadeMax;

    @Basic
    @Column(name = "capacidade_max", nullable = false, precision = 0)
    public double getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(double capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    private String descricao;

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
}
