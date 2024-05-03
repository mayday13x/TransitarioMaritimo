package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "servico", schema = "public", catalog = "transitario_maritimo")
public class ServicoEntity {
    private int id;
    private Integer idFornecedor;
    private Double comissao;
    private Double preco;
    private String descricao;
    private Date dataPrevInicio;
    private Date dataPrevFim;
    private Date dataInicio;
    private Date dataFim;
    private FornecedorEntity fornecedorByIdFornecedor;
    private ServicoTranporteMEntity servicoTranporteMById;
    private ServicoTransporteTEntity servicoTransporteTById;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_fornecedor", nullable = true, insertable=false, updatable=false)
    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Basic
    @Column(name = "comissao", nullable = true, precision = 0)
    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }

    @Basic
    @Column(name = "preco", nullable = true, precision = 0)
    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Basic
    @Column(name = "descricao", nullable = true, length = 255)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

        ServicoEntity that = (ServicoEntity) o;

        if (id != that.id) return false;
        if (idFornecedor != null ? !idFornecedor.equals(that.idFornecedor) : that.idFornecedor != null) return false;
        if (comissao != null ? !comissao.equals(that.comissao) : that.comissao != null) return false;
        if (preco != null ? !preco.equals(that.preco) : that.preco != null) return false;
        if (descricao != null ? !descricao.equals(that.descricao) : that.descricao != null) return false;
        if (dataPrevInicio != null ? !dataPrevInicio.equals(that.dataPrevInicio) : that.dataPrevInicio != null)
            return false;
        if (dataPrevFim != null ? !dataPrevFim.equals(that.dataPrevFim) : that.dataPrevFim != null) return false;
        if (dataInicio != null ? !dataInicio.equals(that.dataInicio) : that.dataInicio != null) return false;
        if (dataFim != null ? !dataFim.equals(that.dataFim) : that.dataFim != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idFornecedor != null ? idFornecedor.hashCode() : 0);
        result = 31 * result + (comissao != null ? comissao.hashCode() : 0);
        result = 31 * result + (preco != null ? preco.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (dataPrevInicio != null ? dataPrevInicio.hashCode() : 0);
        result = 31 * result + (dataPrevFim != null ? dataPrevFim.hashCode() : 0);
        result = 31 * result + (dataInicio != null ? dataInicio.hashCode() : 0);
        result = 31 * result + (dataFim != null ? dataFim.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    public FornecedorEntity getFornecedorByIdFornecedor() {
        return fornecedorByIdFornecedor;
    }

    public void setFornecedorByIdFornecedor(FornecedorEntity fornecedorByIdFornecedor) {
        this.fornecedorByIdFornecedor = fornecedorByIdFornecedor;
    }

    @OneToOne(mappedBy = "servicoByIdServico")
    public ServicoTranporteMEntity getServicoTranporteMById() {
        return servicoTranporteMById;
    }

    public void setServicoTranporteMById(ServicoTranporteMEntity servicoTranporteMById) {
        this.servicoTranporteMById = servicoTranporteMById;
    }

    @OneToOne(mappedBy = "servicoByIdServico")
    public ServicoTransporteTEntity getServicoTransporteTById() {
        return servicoTransporteTById;
    }

    public void setServicoTransporteTById(ServicoTransporteTEntity servicoTransporteTById) {
        this.servicoTransporteTById = servicoTransporteTById;
    }
}
