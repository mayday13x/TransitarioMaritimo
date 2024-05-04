package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "servico_tranporte_m", schema = "public", catalog = "transitario_maritimo")
public class ServicoTranporteMEntity {
    private int idServico;
    private Integer idTransporteM;
    private Date dataPrevInicio;
    private Date dataPrevFim;
    private Date dataInicio;
    private Date dataFim;
    private ServicoEntity servicoByIdServico;
    private TransportemaritimoEntity transportemaritimoByIdTransporteM;

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
    @Column(name = "id_transporte_m", nullable = true, insertable=false, updatable=false)
    public Integer getIdTransporteM() {
        return idTransporteM;
    }

    public void setIdTransporteM(Integer idTransporteM) {
        this.idTransporteM = idTransporteM;
    }

    @Basic
    @Column(name = "data_prev_inicio", nullable = true)
    public Date getDataPrevInicio() {
        return dataPrevInicio;
    }

    public void setDataPrevInicio(Date dataPrevInicio) {
        this.dataPrevInicio = dataPrevInicio;
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

        ServicoTranporteMEntity that = (ServicoTranporteMEntity) o;

        if (idServico != that.idServico) return false;
        if (idTransporteM != null ? !idTransporteM.equals(that.idTransporteM) : that.idTransporteM != null)
            return false;
        if (dataPrevInicio != null ? !dataPrevInicio.equals(that.dataPrevInicio) : that.dataPrevInicio != null)
            return false;
        if (dataPrevFim != null ? !dataPrevFim.equals(that.dataPrevFim) : that.dataPrevFim != null) return false;
        if (dataInicio != null ? !dataInicio.equals(that.dataInicio) : that.dataInicio != null) return false;
        if (dataFim != null ? !dataFim.equals(that.dataFim) : that.dataFim != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idServico;
        result = 31 * result + (idTransporteM != null ? idTransporteM.hashCode() : 0);
        result = 31 * result + (dataPrevInicio != null ? dataPrevInicio.hashCode() : 0);
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
    @JoinColumn(name = "id_transporte_m", referencedColumnName = "id")
    public TransportemaritimoEntity getTransportemaritimoByIdTransporteM() {
        return transportemaritimoByIdTransporteM;
    }

    public void setTransportemaritimoByIdTransporteM(TransportemaritimoEntity transportemaritimoByIdTransporteM) {
        this.transportemaritimoByIdTransporteM = transportemaritimoByIdTransporteM;
    }
}
