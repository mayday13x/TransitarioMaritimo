package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servico_tranporte_m", schema = "public", catalog = "transitario_maritimo")
public class ServicoTranporteMEntity {
    private int idServico;
    private Integer idTransporteM;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicoTranporteMEntity that = (ServicoTranporteMEntity) o;

        if (idServico != that.idServico) return false;
        if (idTransporteM != null ? !idTransporteM.equals(that.idTransporteM) : that.idTransporteM != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idServico;
        result = 31 * result + (idTransporteM != null ? idTransporteM.hashCode() : 0);
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
