package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "cotacao", schema = "public", catalog = "transitario_maritimo")
public class CotacaoEntity {
    private Integer id;
    private Integer idCliente;
    private Integer idEstadoCotacao;
    private Date data;
    private Double valorTotal;
    private Collection<CargaEntity> cargasById;
    private ClienteEntity clienteByIdCliente;
    private EstadoCotacaoEntity estadoCotacaoByIdEstadoCotacao;
    private Collection<FaturaEntity> faturasById;
    private Collection<LinhaCotacaoEntity> linhaCotacaosById;
    private Collection<ReservaEntity> reservasById;

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
    @Column(name = "id_cliente", nullable = true, insertable=false, updatable=false)
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Basic
    @Column(name = "id_estado_cotacao", nullable = true, insertable=false, updatable=false)
    public Integer getIdEstadoCotacao() {
        return idEstadoCotacao;
    }

    public void setIdEstadoCotacao(Integer idEstadoCotacao) {
        this.idEstadoCotacao = idEstadoCotacao;
    }

    @Basic
    @Column(name = "data", nullable = true)
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

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

        CotacaoEntity cotacao = (CotacaoEntity) o;

        if (id != cotacao.id) return false;
        if (idCliente != null ? !idCliente.equals(cotacao.idCliente) : cotacao.idCliente != null) return false;
        if (idEstadoCotacao != null ? !idEstadoCotacao.equals(cotacao.idEstadoCotacao) : cotacao.idEstadoCotacao != null)
            return false;
        if (data != null ? !data.equals(cotacao.data) : cotacao.data != null) return false;
        if (valorTotal != null ? !valorTotal.equals(cotacao.valorTotal) : cotacao.valorTotal != null) return false;

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

    @OneToMany(mappedBy = "cotacaoByIdCotacao")
    public Collection<CargaEntity> getCargasById() {
        return cargasById;
    }

    public void setCargasById(Collection<CargaEntity> cargasById) {
        this.cargasById = cargasById;
    }

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    public ClienteEntity getClienteByIdCliente() {
        return clienteByIdCliente;
    }

    public void setClienteByIdCliente(ClienteEntity clienteByIdCliente) {
        this.clienteByIdCliente = clienteByIdCliente;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado_cotacao", referencedColumnName = "id")
    public EstadoCotacaoEntity getEstadoCotacaoByIdEstadoCotacao() {
        return estadoCotacaoByIdEstadoCotacao;
    }

    public void setEstadoCotacaoByIdEstadoCotacao(EstadoCotacaoEntity estadoCotacaoByIdEstadoCotacao) {
        this.estadoCotacaoByIdEstadoCotacao = estadoCotacaoByIdEstadoCotacao;
    }

    @OneToMany(mappedBy = "cotacaoByIdCotacao")
    public Collection<FaturaEntity> getFaturasById() {
        return faturasById;
    }

    public void setFaturasById(Collection<FaturaEntity> faturasById) {
        this.faturasById = faturasById;
    }

    @OneToMany(mappedBy = "cotacaoByIdCotacao")
    public Collection<LinhaCotacaoEntity> getLinhaCotacaosById() {
        return linhaCotacaosById;
    }

    public void setLinhaCotacaosById(Collection<LinhaCotacaoEntity> linhaCotacaosById) {
        this.linhaCotacaosById = linhaCotacaosById;
    }

    @Transient
    public LinhaCotacaoEntity getFirstLinhaCotacao() {
        if (linhaCotacaosById != null && !linhaCotacaosById.isEmpty()) {
            return linhaCotacaosById.iterator().next();
        }
        return null;
    }

    @OneToMany(mappedBy = "cotacaoByIdCotacao")
    public Collection<ReservaEntity> getReservasById() {
        return reservasById;
    }

    public void setReservasById(Collection<ReservaEntity> reservasById) {
        this.reservasById = reservasById;
    }
}
