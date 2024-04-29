package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "carga", schema = "public", catalog = "transitario_maritimo")
public class CargaEntity {
    private int id;
    private Integer idReserva;
    private Integer idContentor;
    private Integer idArmazem;
    private Integer idEstadoCarga;
    private String nome;
    private Integer quantidade;
    private Double volume;
    private Double peso;
    private Integer idTipoCarga;
    private String localAtual;
    private String observacoes;
    private ReservaEntity reservaByIdReserva;
    private ContentorEntity contentorByIdContentor;
    private ArmazemEntity armazemByIdArmazem;
    private EstadoCargaEntity estadoCargaByIdEstadoCarga;
    private TipoCargaEntity tipoCargaByIdTipoCarga;

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
    @Column(name = "id_reserva", nullable = true, insertable = false, updatable = false)
    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    @Basic
    @Column(name = "id_contentor", nullable = true, insertable = false, updatable = false)
    public Integer getIdContentor() {
        return idContentor;
    }

    public void setIdContentor(Integer idContentor) {
        this.idContentor = idContentor;
    }

    @Basic
    @Column(name = "id_armazem", nullable = true, insertable = false, updatable = false)
    public Integer getIdArmazem() {
        return idArmazem;
    }

    public void setIdArmazem(Integer idArmazem) {
        this.idArmazem = idArmazem;
    }

    @Basic
    @Column(name = "id_estado_carga", nullable = true, insertable = false, updatable = false)
    public Integer getIdEstadoCarga() {
        return idEstadoCarga;
    }

    public void setIdEstadoCarga(Integer idEstadoCarga) {
        this.idEstadoCarga = idEstadoCarga;
    }

    @Basic
    @Column(name = "nome", nullable = true, length = 255)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "quantidade", nullable = true)
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Basic
    @Column(name = "volume", nullable = true, precision = 0)
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "peso", nullable = true, precision = 0)
    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Basic
    @Column(name = "id_tipo_carga", nullable = true, insertable = false, updatable = false)
    public Integer getIdTipoCarga() {
        return idTipoCarga;
    }

    public void setIdTipoCarga(Integer idTipoCarga) {
        this.idTipoCarga = idTipoCarga;
    }

    @Basic
    @Column(name = "local_atual", nullable = true, length = 255)
    public String getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(String localAtual) {
        this.localAtual = localAtual;
    }

    @Basic
    @Column(name = "observacoes", nullable = true, length = -1)
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CargaEntity that = (CargaEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(idReserva, that.idReserva)) return false;
        if (!Objects.equals(idContentor, that.idContentor)) return false;
        if (!Objects.equals(idArmazem, that.idArmazem)) return false;
        if (!Objects.equals(idEstadoCarga, that.idEstadoCarga))
            return false;
        if (!Objects.equals(nome, that.nome)) return false;
        if (!Objects.equals(quantidade, that.quantidade)) return false;
        if (!Objects.equals(volume, that.volume)) return false;
        if (!Objects.equals(peso, that.peso)) return false;
        if (!Objects.equals(idTipoCarga, that.idTipoCarga)) return false;
        if (!Objects.equals(localAtual, that.localAtual)) return false;
        if (!Objects.equals(observacoes, that.observacoes)) return false;

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
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_reserva", referencedColumnName = "id")
    public ReservaEntity getReservaByIdReserva() {
        return reservaByIdReserva;
    }

    public void setReservaByIdReserva(ReservaEntity reservaByIdReserva) {
        this.reservaByIdReserva = reservaByIdReserva;
    }

    @ManyToOne
    @JoinColumn(name = "id_contentor", referencedColumnName = "cin")
    public ContentorEntity getContentorByIdContentor() {
        return contentorByIdContentor;
    }

    public void setContentorByIdContentor(ContentorEntity contentorByIdContentor) {
        this.contentorByIdContentor = contentorByIdContentor;
    }

    @ManyToOne
    @JoinColumn(name = "id_armazem", referencedColumnName = "id")
    public ArmazemEntity getArmazemByIdArmazem() {
        return armazemByIdArmazem;
    }

    public void setArmazemByIdArmazem(ArmazemEntity armazemByIdArmazem) {
        this.armazemByIdArmazem = armazemByIdArmazem;
    }

    @ManyToOne
    @JoinColumn(name = "id_estado_carga", referencedColumnName = "id")
    public EstadoCargaEntity getEstadoCargaByIdEstadoCarga() {
        return estadoCargaByIdEstadoCarga;
    }

    public void setEstadoCargaByIdEstadoCarga(EstadoCargaEntity estadoCargaByIdEstadoCarga) {
        this.estadoCargaByIdEstadoCarga = estadoCargaByIdEstadoCarga;
    }

    @ManyToOne
    @JoinColumn(name = "id_tipo_carga", referencedColumnName = "id")
    public TipoCargaEntity getTipoCargaByIdTipoCarga() {
        return tipoCargaByIdTipoCarga;
    }

    public void setTipoCargaByIdTipoCarga(TipoCargaEntity tipoCargaByIdTipoCarga) {
        this.tipoCargaByIdTipoCarga = tipoCargaByIdTipoCarga;
    }
}
