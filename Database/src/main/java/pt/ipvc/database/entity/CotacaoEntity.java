package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@jakarta.persistence.Table(name = "cotacao", schema = "public", catalog = "transitario_maritimo")
public class CotacaoEntity {
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

    private Integer idCliente;

    @Basic
    @Column(name = "id_cliente", nullable = true)
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    private Integer idEstadoCotacao;

    @Basic
    @Column(name = "id_estado_cotacao", nullable = true)
    public Integer getIdEstadoCotacao() {
        return idEstadoCotacao;
    }

    public void setIdEstadoCotacao(Integer idEstadoCotacao) {
        this.idEstadoCotacao = idEstadoCotacao;
    }

    private Date data;

    @Basic
    @Column(name = "data", nullable = true)
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    private Double valorTotal;

    @Basic
    @Column(name = "valor_total", nullable = true, precision = 0)
    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CotacaoEntity that = (CotacaoEntity) o;

        if (id != that.id) return false;
        if (idCliente != null ? !idCliente.equals(that.idCliente) : that.idCliente != null) return false;
        if (idEstadoCotacao != null ? !idEstadoCotacao.equals(that.idEstadoCotacao) : that.idEstadoCotacao != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (valorTotal != null ? !valorTotal.equals(that.valorTotal) : that.valorTotal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idCliente != null ? idCliente.hashCode() : 0);
        result = 31 * result + (idEstadoCotacao != null ? idEstadoCotacao.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (valorTotal != null ? valorTotal.hashCode() : 0);
        return result;
    }
}
