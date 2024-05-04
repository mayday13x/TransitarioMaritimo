package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "reserva", schema = "public", catalog = "transitario_maritimo")
public class ReservaEntity {
    private Integer id;
    private Integer idFuncionario;
    private Integer idCliente;
    private Integer idEstadoReserva;
    private Date data;
    private String origem;
    private String destino;
    private Integer idCotacao;
    private Integer idTransporteMaritimo;
    private Collection<CargaEntity> cargasById;
    private FuncionarioEntity funcionarioByIdFuncionario;
    private ClienteEntity clienteByIdCliente;
    private EstadoReservaEntity estadoReservaByIdEstadoReserva;
    private CotacaoEntity cotacaoByIdCotacao;
    private TransportemaritimoEntity transportemaritimoByIdTransporteMaritimo;

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
    @Column(name = "id_funcionario", nullable = true, insertable=false, updatable=false)
    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
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
    @Column(name = "id_estado_reserva", nullable = true, insertable=false, updatable=false)
    public Integer getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(Integer idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
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
    @Column(name = "origem", nullable = true, length = 255)
    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Basic
    @Column(name = "destino", nullable = true, length = 255)
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Basic
    @Column(name = "id_cotacao", nullable = true, insertable=false, updatable=false)
    public Integer getIdCotacao() {
        return idCotacao;
    }

    public void setIdCotacao(Integer idCotacao) {
        this.idCotacao = idCotacao;
    }

    @Basic
    @Column(name = "id_transporte_maritimo", nullable = true, insertable=false, updatable=false)
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

        ReservaEntity that = (ReservaEntity) o;

        if (id != that.id) return false;
        if (idFuncionario != null ? !idFuncionario.equals(that.idFuncionario) : that.idFuncionario != null)
            return false;
        if (idCliente != null ? !idCliente.equals(that.idCliente) : that.idCliente != null) return false;
        if (idEstadoReserva != null ? !idEstadoReserva.equals(that.idEstadoReserva) : that.idEstadoReserva != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (origem != null ? !origem.equals(that.origem) : that.origem != null) return false;
        if (destino != null ? !destino.equals(that.destino) : that.destino != null) return false;
        if (idCotacao != null ? !idCotacao.equals(that.idCotacao) : that.idCotacao != null) return false;
        if (idTransporteMaritimo != null ? !idTransporteMaritimo.equals(that.idTransporteMaritimo) : that.idTransporteMaritimo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idFuncionario != null ? idFuncionario.hashCode() : 0);
        result = 31 * result + (idCliente != null ? idCliente.hashCode() : 0);
        result = 31 * result + (idEstadoReserva != null ? idEstadoReserva.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (origem != null ? origem.hashCode() : 0);
        result = 31 * result + (destino != null ? destino.hashCode() : 0);
        result = 31 * result + (idCotacao != null ? idCotacao.hashCode() : 0);
        result = 31 * result + (idTransporteMaritimo != null ? idTransporteMaritimo.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reservaByIdReserva")
    public Collection<CargaEntity> getCargasById() {
        return cargasById;
    }

    public void setCargasById(Collection<CargaEntity> cargasById) {
        this.cargasById = cargasById;
    }

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    public FuncionarioEntity getFuncionarioByIdFuncionario() {
        return funcionarioByIdFuncionario;
    }

    public void setFuncionarioByIdFuncionario(FuncionarioEntity funcionarioByIdFuncionario) {
        this.funcionarioByIdFuncionario = funcionarioByIdFuncionario;
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
    @JoinColumn(name = "id_estado_reserva", referencedColumnName = "id")
    public EstadoReservaEntity getEstadoReservaByIdEstadoReserva() {
        return estadoReservaByIdEstadoReserva;
    }

    public void setEstadoReservaByIdEstadoReserva(EstadoReservaEntity estadoReservaByIdEstadoReserva) {
        this.estadoReservaByIdEstadoReserva = estadoReservaByIdEstadoReserva;
    }

    @ManyToOne
    @JoinColumn(name = "id_cotacao", referencedColumnName = "id")
    public CotacaoEntity getCotacaoByIdCotacao() {
        return cotacaoByIdCotacao;
    }

    public void setCotacaoByIdCotacao(CotacaoEntity cotacaoByIdCotacao) {
        this.cotacaoByIdCotacao = cotacaoByIdCotacao;
    }

    @ManyToOne
    @JoinColumn(name = "id_transporte_maritimo", referencedColumnName = "id")
    public TransportemaritimoEntity getTransportemaritimoByIdTransporteMaritimo() {
        return transportemaritimoByIdTransporteMaritimo;
    }

    public void setTransportemaritimoByIdTransporteMaritimo(TransportemaritimoEntity transportemaritimoByIdTransporteMaritimo) {
        this.transportemaritimoByIdTransporteMaritimo = transportemaritimoByIdTransporteMaritimo;
    }
}
