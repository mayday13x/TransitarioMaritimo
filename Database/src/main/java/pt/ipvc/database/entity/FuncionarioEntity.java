package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "funcionario", schema = "public", catalog = "transitario_maritimo")
public class FuncionarioEntity {
    private Integer id;
    private String nome;
    private Integer nif;
    private String rua;
    private Integer porta;
    private Integer idCodPostal;
    private String telefone;
    private String email;
    private Integer idTipoFuncionario;
    private String utilizador;
    private String password;
    private Integer idArmazem;
    private Collection<ClienteEntity> clientesById;
    private CodPostalEntity codPostalByIdCodPostal;
    private TipoFuncionarioEntity tipoFuncionarioByIdTipoFuncionario;
    private ArmazemEntity armazemByIdArmazem;
    private Collection<ReservaEntity> reservasById;
    private Collection<TransportemaritimoEntity> transportemaritimosById;

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
    @Column(name = "nome", nullable = true, length = 255)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "nif", nullable = true)
    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    @Basic
    @Column(name = "rua", nullable = true, length = 255)
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    @Basic
    @Column(name = "porta", nullable = true)
    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    @Basic
    @Column(name = "id_cod_postal", nullable = true, insertable=false, updatable=false)
    public Integer getIdCodPostal() {
        return idCodPostal;
    }

    public void setIdCodPostal(Integer idCodPostal) {
        this.idCodPostal = idCodPostal;
    }

    @Basic
    @Column(name = "telefone", nullable = true, length = 20)
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "id_tipo_funcionario", nullable = true, insertable=false, updatable=false)
    public Integer getIdTipoFuncionario() {
        return idTipoFuncionario;
    }

    public void setIdTipoFuncionario(Integer idTipoFuncionario) {
        this.idTipoFuncionario = idTipoFuncionario;
    }

    @Basic
    @Column(name = "utilizador", nullable = false, length = 50)
    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "id_armazem", nullable = true, insertable=false, updatable=false)
    public Integer getIdArmazem() {
        return idArmazem;
    }

    public void setIdArmazem(Integer idArmazem) {
        this.idArmazem = idArmazem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuncionarioEntity that = (FuncionarioEntity) o;

        if (id != that.id) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (nif != null ? !nif.equals(that.nif) : that.nif != null) return false;
        if (rua != null ? !rua.equals(that.rua) : that.rua != null) return false;
        if (porta != null ? !porta.equals(that.porta) : that.porta != null) return false;
        if (idCodPostal != null ? !idCodPostal.equals(that.idCodPostal) : that.idCodPostal != null) return false;
        if (telefone != null ? !telefone.equals(that.telefone) : that.telefone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (idTipoFuncionario != null ? !idTipoFuncionario.equals(that.idTipoFuncionario) : that.idTipoFuncionario != null)
            return false;
        if (utilizador != null ? !utilizador.equals(that.utilizador) : that.utilizador != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (idArmazem != null ? !idArmazem.equals(that.idArmazem) : that.idArmazem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (nif != null ? nif.hashCode() : 0);
        result = 31 * result + (rua != null ? rua.hashCode() : 0);
        result = 31 * result + (porta != null ? porta.hashCode() : 0);
        result = 31 * result + (idCodPostal != null ? idCodPostal.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (idTipoFuncionario != null ? idTipoFuncionario.hashCode() : 0);
        result = 31 * result + (utilizador != null ? utilizador.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (idArmazem != null ? idArmazem.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "funcionarioByIdFuncionario")
    public Collection<ClienteEntity> getClientesById() {
        return clientesById;
    }

    public void setClientesById(Collection<ClienteEntity> clientesById) {
        this.clientesById = clientesById;
    }

    @ManyToOne
    @JoinColumn(name = "id_cod_postal", referencedColumnName = "id_cod_postal")
    public CodPostalEntity getCodPostalByIdCodPostal() {
        return codPostalByIdCodPostal;
    }

    public void setCodPostalByIdCodPostal(CodPostalEntity codPostalByIdCodPostal) {
        this.codPostalByIdCodPostal = codPostalByIdCodPostal;
    }

    @ManyToOne
    @JoinColumn(name = "id_tipo_funcionario", referencedColumnName = "id")
    public TipoFuncionarioEntity getTipoFuncionarioByIdTipoFuncionario() {
        return tipoFuncionarioByIdTipoFuncionario;
    }

    public void setTipoFuncionarioByIdTipoFuncionario(TipoFuncionarioEntity tipoFuncionarioByIdTipoFuncionario) {
        this.tipoFuncionarioByIdTipoFuncionario = tipoFuncionarioByIdTipoFuncionario;
    }

    @ManyToOne
    @JoinColumn(name = "id_armazem", referencedColumnName = "id")
    public ArmazemEntity getArmazemByIdArmazem() {
        return armazemByIdArmazem;
    }

    public void setArmazemByIdArmazem(ArmazemEntity armazemByIdArmazem) {
        this.armazemByIdArmazem = armazemByIdArmazem;
    }

    @OneToMany(mappedBy = "funcionarioByIdFuncionario")
    public Collection<ReservaEntity> getReservasById() {
        return reservasById;
    }

    public void setReservasById(Collection<ReservaEntity> reservasById) {
        this.reservasById = reservasById;
    }

    @OneToMany(mappedBy = "funcionarioByIdFuncionario")
    public Collection<TransportemaritimoEntity> getTransportemaritimosById() {
        return transportemaritimosById;
    }

    public void setTransportemaritimosById(Collection<TransportemaritimoEntity> transportemaritimosById) {
        this.transportemaritimosById = transportemaritimosById;
    }
}
