package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "contentor", schema = "public", catalog = "transitario_maritimo")
public class ContentorEntity {
    private int cin;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "cin", nullable = false)
    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    private Integer idEstadoContentor;

    @Basic
    @Column(name = "id_estado_contentor", nullable = true)
    public Integer getIdEstadoContentor() {
        return idEstadoContentor;
    }

    public void setIdEstadoContentor(Integer idEstadoContentor) {
        this.idEstadoContentor = idEstadoContentor;
    }

    private Double capacidade;

    @Basic
    @Column(name = "capacidade", nullable = true, precision = 0)
    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    private Double pesoMax;

    @Basic
    @Column(name = "peso_max", nullable = true, precision = 0)
    public Double getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(Double pesoMax) {
        this.pesoMax = pesoMax;
    }

    private String localAtual;

    @Basic
    @Column(name = "local_atual", nullable = true, length = 255)
    public String getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(String localAtual) {
        this.localAtual = localAtual;
    }

    private Integer tipoContentor;

    @Basic
    @Column(name = "tipo_contentor", nullable = true)
    public Integer getTipoContentor() {
        return tipoContentor;
    }

    public void setTipoContentor(Integer tipoContentor) {
        this.tipoContentor = tipoContentor;
    }

    private Integer idTransporteMaritimo;

    @Basic
    @Column(name = "id_transporte_maritimo", nullable = true)
    public Integer getIdTransporteMaritimo() {
        return idTransporteMaritimo;
    }

    public void setIdTransporteMaritimo(Integer idTransporteMaritimo) {
        this.idTransporteMaritimo = idTransporteMaritimo;
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
        if (idTransporteMaritimo != null ? !idTransporteMaritimo.equals(that.idTransporteMaritimo) : that.idTransporteMaritimo != null)
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
        result = 31 * result + (idTransporteMaritimo != null ? idTransporteMaritimo.hashCode() : 0);
        return result;
    }
}
