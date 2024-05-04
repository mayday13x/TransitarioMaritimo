package pt.ipvc.database.entity;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "carga", schema = "public", catalog = "transitario_maritimo")
public class CargaEntity {
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

    private Integer idReserva;

    @Basic
    @Column(name = "id_reserva", nullable = true)
    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    private Integer idContentor;

    @Basic
    @Column(name = "id_contentor", nullable = true)
    public Integer getIdContentor() {
        return idContentor;
    }

    public void setIdContentor(Integer idContentor) {
        this.idContentor = idContentor;
    }

    private Integer idArmazem;

    @Basic
    @Column(name = "id_armazem", nullable = true)
    public Integer getIdArmazem() {
        return idArmazem;
    }

    public void setIdArmazem(Integer idArmazem) {
        this.idArmazem = idArmazem;
    }

    private Integer idEstadoCarga;

    @Basic
    @Column(name = "id_estado_carga", nullable = true)
    public Integer getIdEstadoCarga() {
        return idEstadoCarga;
    }

    public void setIdEstadoCarga(Integer idEstadoCarga) {
        this.idEstadoCarga = idEstadoCarga;
    }

    private String nome;

    @Basic
    @Column(name = "nome", nullable = true, length = 255)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private Integer quantidade;

    @Basic
    @Column(name = "quantidade", nullable = true)
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    private Double volume;

    @Basic
    @Column(name = "volume", nullable = true, precision = 0)
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    private Double peso;

    @Basic
    @Column(name = "peso", nullable = true, precision = 0)
    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    private Integer idTipoCarga;

    @Basic
    @Column(name = "id_tipo_carga", nullable = true)
    public Integer getIdTipoCarga() {
        return idTipoCarga;
    }

    public void setIdTipoCarga(Integer idTipoCarga) {
        this.idTipoCarga = idTipoCarga;
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

    private String observacoes;

    @Basic
    @Column(name = "observacoes", nullable = true, length = -1)
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    private Integer idCotacao;

    @Basic
    @Column(name = "id_cotacao", nullable = true)
    public Integer getIdCotacao() {
        return idCotacao;
    }

    public void setIdCotacao(Integer idCotacao) {
        this.idCotacao = idCotacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargaEntity that = (CargaEntity) o;

        if (id != that.id) return false;
        if (idReserva != null ? !idReserva.equals(that.idReserva) : that.idReserva != null) return false;
        if (idContentor != null ? !idContentor.equals(that.idContentor) : that.idContentor != null) return false;
        if (idArmazem != null ? !idArmazem.equals(that.idArmazem) : that.idArmazem != null) return false;
        if (idEstadoCarga != null ? !idEstadoCarga.equals(that.idEstadoCarga) : that.idEstadoCarga != null)
            return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (quantidade != null ? !quantidade.equals(that.quantidade) : that.quantidade != null) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
        if (peso != null ? !peso.equals(that.peso) : that.peso != null) return false;
        if (idTipoCarga != null ? !idTipoCarga.equals(that.idTipoCarga) : that.idTipoCarga != null) return false;
        if (localAtual != null ? !localAtual.equals(that.localAtual) : that.localAtual != null) return false;
        if (observacoes != null ? !observacoes.equals(that.observacoes) : that.observacoes != null) return false;
        if (idCotacao != null ? !idCotacao.equals(that.idCotacao) : that.idCotacao != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idReserva != null ? idReserva.hashCode() : 0);
        result = 31 * result + (idContentor != null ? idContentor.hashCode() : 0);
        result = 31 * result + (idArmazem != null ? idArmazem.hashCode() : 0);
        result = 31 * result + (idEstadoCarga != null ? idEstadoCarga.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (peso != null ? peso.hashCode() : 0);
        result = 31 * result + (idTipoCarga != null ? idTipoCarga.hashCode() : 0);
        result = 31 * result + (localAtual != null ? localAtual.hashCode() : 0);
        result = 31 * result + (observacoes != null ? observacoes.hashCode() : 0);
        result = 31 * result + (idCotacao != null ? idCotacao.hashCode() : 0);
        return result;
    }
}
