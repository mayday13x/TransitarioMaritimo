package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "linha_cotacao", schema = "public", catalog = "transitario_maritimo")
public class LinhaCotacaoEntity {
    private int idCotacao;
    private int idServico;
    private Date dataPrevInicio;
    private Date dataPrevFim;
    private Date dataInicio;
    private Date dataFim;
    private int id;
    private CotacaoEntity cotacaoByIdCotacao;
    private ServicoEntity servicoByIdServico;

    @Basic
    @Column(name = "id_cotacao", nullable = false, insertable=false, updatable=false)
    public int getIdCotacao() {
        return idCotacao;
    }

    public void setIdCotacao(int idCotacao) {
        this.idCotacao = idCotacao;
    }

    @Basic
    @Column(name = "id_servico", nullable = false, insertable=false, updatable=false)
    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaCotacaoEntity that = (LinhaCotacaoEntity) o;

        if (idCotacao != that.idCotacao) return false;
        if (idServico != that.idServico) return false;
        if (id != that.id) return false;
        if (dataPrevInicio != null ? !dataPrevInicio.equals(that.dataPrevInicio) : that.dataPrevInicio != null)
            return false;
        if (dataPrevFim != null ? !dataPrevFim.equals(that.dataPrevFim) : that.dataPrevFim != null) return false;
        if (dataInicio != null ? !dataInicio.equals(that.dataInicio) : that.dataInicio != null) return false;
        if (dataFim != null ? !dataFim.equals(that.dataFim) : that.dataFim != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCotacao;
        result = 31 * result + idServico;
        result = 31 * result + (dataPrevInicio != null ? dataPrevInicio.hashCode() : 0);
        result = 31 * result + (dataPrevFim != null ? dataPrevFim.hashCode() : 0);
        result = 31 * result + (dataInicio != null ? dataInicio.hashCode() : 0);
        result = 31 * result + (dataFim != null ? dataFim.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_cotacao", referencedColumnName = "id", nullable = false)
    public CotacaoEntity getCotacaoByIdCotacao() {
        return cotacaoByIdCotacao;
    }

    public void setCotacaoByIdCotacao(CotacaoEntity cotacaoByIdCotacao) {
        this.cotacaoByIdCotacao = cotacaoByIdCotacao;
    }

    @ManyToOne
    @JoinColumn(name = "id_servico", referencedColumnName = "id", nullable = false)
    public ServicoEntity getServicoByIdServico() {
        return servicoByIdServico;
    }

    public void setServicoByIdServico(ServicoEntity servicoByIdServico) {
        this.servicoByIdServico = servicoByIdServico;
    }
}
