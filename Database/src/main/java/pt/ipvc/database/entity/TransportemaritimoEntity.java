package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "transportemaritimo", schema = "public", catalog = "transitario_maritimo")
public class TransportemaritimoEntity {
    private int id;
    private String imo;
    private String portoOrigem;
    private String portoDestino;
    private Integer idFuncionario;
    private Collection<ContentorEntity> contentorsById;
    private Collection<ReservaEntity> reservasById;
    private FuncionarioEntity funcionarioByIdFuncionario;

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
    @Column(name = "imo", nullable = true, length = 255)
    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    @Basic
    @Column(name = "porto_origem", nullable = true, length = 255)
    public String getPortoOrigem() {
        return portoOrigem;
    }

    public void setPortoOrigem(String portoOrigem) {
        this.portoOrigem = portoOrigem;
    }

    @Basic
    @Column(name = "porto_destino", nullable = true, length = 255)
    public String getPortoDestino() {
        return portoDestino;
    }

    public void setPortoDestino(String portoDestino) {
        this.portoDestino = portoDestino;
    }

    @Basic
    @Column(name = "id_funcionario", nullable = true)
    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportemaritimoEntity that = (TransportemaritimoEntity) o;

        if (id != that.id) return false;
        if (imo != null ? !imo.equals(that.imo) : that.imo != null) return false;
        if (portoOrigem != null ? !portoOrigem.equals(that.portoOrigem) : that.portoOrigem != null) return false;
        if (portoDestino != null ? !portoDestino.equals(that.portoDestino) : that.portoDestino != null) return false;
        if (idFuncionario != null ? !idFuncionario.equals(that.idFuncionario) : that.idFuncionario != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imo != null ? imo.hashCode() : 0);
        result = 31 * result + (portoOrigem != null ? portoOrigem.hashCode() : 0);
        result = 31 * result + (portoDestino != null ? portoDestino.hashCode() : 0);
        result = 31 * result + (idFuncionario != null ? idFuncionario.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transportemaritimoByIdTransporteMaritimo")
    public Collection<ContentorEntity> getContentorsById() {
        return contentorsById;
    }

    public void setContentorsById(Collection<ContentorEntity> contentorsById) {
        this.contentorsById = contentorsById;
    }

    @OneToMany(mappedBy = "transportemaritimoByIdTransporteMaritimo")
    public Collection<ReservaEntity> getReservasById() {
        return reservasById;
    }

    public void setReservasById(Collection<ReservaEntity> reservasById) {
        this.reservasById = reservasById;
    }

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    public FuncionarioEntity getFuncionarioByIdFuncionario() {
        return funcionarioByIdFuncionario;
    }

    public void setFuncionarioByIdFuncionario(FuncionarioEntity funcionarioByIdFuncionario) {
        this.funcionarioByIdFuncionario = funcionarioByIdFuncionario;
    }
}
