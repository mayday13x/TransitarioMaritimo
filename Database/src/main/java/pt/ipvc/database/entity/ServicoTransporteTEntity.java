package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servico_transporte_t", schema = "public", catalog = "transitario_maritimo")
public class ServicoTransporteTEntity {
    private int idServico;
    private Integer idTransporteT;
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
    @Column(name = "id_transporte_t", nullable = true, insertable = false, updatable = false)
    public Integer getIdTransporteT() {
        return idTransporteT;
    }

    public void setIdTransporteT(Integer idTransporteT) {
        this.idTransporteT = idTransporteT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicoTransporteTEntity that = (ServicoTransporteTEntity) o;

        if (idServico != that.idServico) return false;
        if (idTransporteT != null ? !idTransporteT.equals(that.idTransporteT) : that.idTransporteT != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idServico;
        result = 31 * result + (idTransporteT != null ? idTransporteT.hashCode() : 0);
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
