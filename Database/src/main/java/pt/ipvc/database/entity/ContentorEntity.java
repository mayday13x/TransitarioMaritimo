package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "contentor", schema = "public", catalog = "transitario_maritimo")
public class ContentorEntity {
    private Integer cin;
    private Integer idEstadoContentor;
    private Double capacidade;
    private Double pesoMax;
    private String localAtual;
    private Integer tipoContentor;
    private Collection<CargaEntity> cargasByCin;
    private EstadoContentorEntity estadoContentorByIdEstadoContentor;
    private TipoContentorEntity tipoContentorByTipoContentor;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cin", nullable = false)
    public Integer getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    @Basic
    @Column(name = "id_estado_contentor", nullable = true, insertable=false, updatable=false)
    public Integer getIdEstadoContentor() {
        return idEstadoContentor;
    }

    public void setIdEstadoContentor(Integer idEstadoContentor) {
        this.idEstadoContentor = idEstadoContentor;
    }

    @Basic
    @Column(name = "capacidade", nullable = true, precision = 0)
    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    @Basic
    @Column(name = "peso_max", nullable = true, precision = 0)
    public Double getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(Double pesoMax) {
        this.pesoMax = pesoMax;
    }

    @Basic
    @Column(name = "local_atual", nullable = true, length = 255)
    public String getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(String localAtual) {
        this.localAtual = localAtual;
    }

    @Basic
    @Column(name = "tipo_contentor", nullable = true, insertable=false, updatable=false)
    public Integer getTipoContentor() {
        return tipoContentor;
    }

    public void setTipoContentor(Integer tipoContentor) {
        this.tipoContentor = tipoContentor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentorEntity that = (ContentorEntity) o;

        if (cin != that.cin) return false;
        if (idEstadoContentor != null ? !idEstadoContentor.equals(that.idEstadoContentor) : that.idEstadoContentor != null)
            return false;
        if (capacidade != null ? !capacidade.equals(that.capacidade) : that.capacidade != null) return false;
        if (pesoMax != null ? !pesoMax.equals(that.pesoMax) : that.pesoMax != null) return false;
        if (localAtual != null ? !localAtual.equals(that.localAtual) : that.localAtual != null) return false;
        if (tipoContentor != null ? !tipoContentor.equals(that.tipoContentor) : that.tipoContentor != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cin;
        result = 31 * result + (idEstadoContentor != null ? idEstadoContentor.hashCode() : 0);
        result = 31 * result + (capacidade != null ? capacidade.hashCode() : 0);
        result = 31 * result + (pesoMax != null ? pesoMax.hashCode() : 0);
        result = 31 * result + (localAtual != null ? localAtual.hashCode() : 0);
        result = 31 * result + (tipoContentor != null ? tipoContentor.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "contentorByIdContentor")
    public Collection<CargaEntity> getCargasByCin() {
        return cargasByCin;
    }

    public void setCargasByCin(Collection<CargaEntity> cargasByCin) {
        this.cargasByCin = cargasByCin;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado_contentor", referencedColumnName = "id")
    public EstadoContentorEntity getEstadoContentorByIdEstadoContentor() {
        return estadoContentorByIdEstadoContentor;
    }

    public void setEstadoContentorByIdEstadoContentor(EstadoContentorEntity estadoContentorByIdEstadoContentor) {
        this.estadoContentorByIdEstadoContentor = estadoContentorByIdEstadoContentor;
    }

    @ManyToOne
    @JoinColumn(name = "tipo_contentor", referencedColumnName = "id")
    public TipoContentorEntity getTipoContentorByTipoContentor() {
        return tipoContentorByTipoContentor;
    }

    public void setTipoContentorByTipoContentor(TipoContentorEntity tipoContentorByTipoContentor) {
        this.tipoContentorByTipoContentor = tipoContentorByTipoContentor;
    }
}
