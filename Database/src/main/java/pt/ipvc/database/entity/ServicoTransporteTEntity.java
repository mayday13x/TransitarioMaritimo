package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "servico_transporte_t", schema = "public", catalog = "transitario_maritimo")
public class ServicoTransporteTEntity {
    private int idServico;
    private Integer idTransporteT;
    private Date dataPrevIncio;
    private Date dataPrevFim;
    private Date dataInicio;
    private Date dataFim;
    private ServicoEntity servicoByIdServico;
    private TransporteterrestreEntity transporteterrestreByIdTransporteT;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_servico", nullable = false)
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    @Basic
    @Column(name = "id_transporte_t", nullable = true, insertable=false, updatable=false)
    public Integer getIdTransporteT() {
        return idTransporteT;
    }

    public void setIdTransporteT(Integer idTransporteT) {
        this.idTransporteT = idTransporteT;
    }

    @Basic
    @Column(name = "data_prev_incio", nullable = true)
    public Date getDataPrevIncio() {
        return dataPrevIncio;
    }

    public void setDataPrevIncio(Date dataPrevIncio) {
        this.dataPrevIncio = dataPrevIncio;
    }

    @Basic
    @Column(name = "data_prev_fim", nullable = true)
    public Date getDataPrevFim() {
        return dataPrevFim;
    }

    public void setDataPrevFim(Date dataPrevFim) {
        this.dataPrevFim = dataPrevFim;
    }

    @Basic
    @Column(name = "data_inicio", nullable = true)
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Basic
    @Column(name = "data_fim", nullable = true)
    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicoTransporteTEntity that = (ServicoTransporteTEntity) o;

        if (idServico != that.idServico) return false;
        if (idTransporteT != null ? !idTransporteT.equals(that.idTransporteT) : that.idTransporteT != null)
            return false;
        if (dataPrevIncio != null ? !dataPrevIncio.equals(that.dataPrevIncio) : that.dataPrevIncio != null)
            return false;
        if (dataPrevFim != null ? !dataPrevFim.equals(that.dataPrevFim) : that.dataPrevFim != null) return false;
        if (dataInicio != null ? !dataInicio.equals(that.dataInicio) : that.dataInicio != null) return false;
        if (dataFim != null ? !dataFim.equals(that.dataFim) : that.dataFim != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idServico;
        result = 31 * result + (idTransporteT != null ? idTransporteT.hashCode() : 0);
        result = 31 * result + (dataPrevIncio != null ? dataPrevIncio.hashCode() : 0);
        result = 31 * result + (dataPrevFim != null ? dataPrevFim.hashCode() : 0);
        result = 31 * result + (dataInicio != null ? dataInicio.hashCode() : 0);
        result = 31 * result + (dataFim != null ? dataFim.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "id_servico", referencedColumnName = "id", nullable = false)
    public ServicoEntity getServicoByIdServico() {
        return servicoByIdServico;
    }

    public void setServicoByIdServico(ServicoEntity servicoByIdServico) {
        this.servicoByIdServico = servicoByIdServico;
    }

    @ManyToOne
    @JoinColumn(name = "id_transporte_t", referencedColumnName = "id")
    public TransporteterrestreEntity getTransporteterrestreByIdTransporteT() {
        return transporteterrestreByIdTransporteT;
    }

    public void setTransporteterrestreByIdTransporteT(TransporteterrestreEntity transporteterrestreByIdTransporteT) {
        this.transporteterrestreByIdTransporteT = transporteterrestreByIdTransporteT;
    }
}
