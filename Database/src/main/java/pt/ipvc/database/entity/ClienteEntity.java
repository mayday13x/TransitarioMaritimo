package pt.ipvc.database.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Collection;

@Entity
@Table(name = "cliente", schema = "public", catalog = "transitario_maritimo")
public class ClienteEntity {
    private Integer id;
    private String nome;
    private Integer nif;
    private String rua;
    private Integer porta;
    private Integer idCodPostal;
    private String telefone;
    private String email;
    private String utilizador;
    private String password;
    private Integer idFuncionario;
    private CodPostalEntity codPostalByIdCodPostal;
    private FuncionarioEntity funcionarioByIdFuncionario;
    private Collection<CotacaoEntity> cotacaosById;
    private Collection<ReservaEntity> reservasById;

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
    @Column(name = "nome", nullable = false, length = 255)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "nif", nullable = false)
    public Integer getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    @Basic
    @Column(name = "rua", nullable = false, length = 255)
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    @Basic
    @Column(name = "porta", nullable = false)
    public Integer getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    @Basic
    @Column(name = "id_cod_postal", nullable = false,insertable=false, updatable=false)
    public Integer getIdCodPostal() {
        return idCodPostal;
    }

    public void setIdCodPostal(int idCodPostal) {
        this.idCodPostal = idCodPostal;
    }

    @Basic
    @Column(name = "telefone", nullable = false, length = 20)
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
    @Column(name = "id_funcionario", nullable = true, insertable=false, updatable=false)
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

        ClienteEntity that = (ClienteEntity) o;

        if (id != that.id) return false;
        if (nif != that.nif) return false;
        if (porta != that.porta) return false;
        if (idCodPostal != that.idCodPostal) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (rua != null ? !rua.equals(that.rua) : that.rua != null) return false;
        if (telefone != null ? !telefone.equals(that.telefone) : that.telefone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (utilizador != null ? !utilizador.equals(that.utilizador) : that.utilizador != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (idFuncionario != null ? !idFuncionario.equals(that.idFuncionario) : that.idFuncionario != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + nif;
        result = 31 * result + (rua != null ? rua.hashCode() : 0);
        result = 31 * result + porta;
        result = 31 * result + idCodPostal;
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (utilizador != null ? utilizador.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (idFuncionario != null ? idFuncionario.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_cod_postal", referencedColumnName = "id_cod_postal", nullable = false)
    public CodPostalEntity getCodPostalByIdCodPostal() {
        return codPostalByIdCodPostal;
    }

    public void setCodPostalByIdCodPostal(CodPostalEntity codPostalByIdCodPostal) {
        this.codPostalByIdCodPostal = codPostalByIdCodPostal;
    }

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    public FuncionarioEntity getFuncionarioByIdFuncionario() {
        return funcionarioByIdFuncionario;
    }

    public void setFuncionarioByIdFuncionario(FuncionarioEntity funcionarioByIdFuncionario) {
        this.funcionarioByIdFuncionario = funcionarioByIdFuncionario;
    }

    @OneToMany(mappedBy = "clienteByIdCliente")
    public Collection<CotacaoEntity> getCotacaosById() {
        return cotacaosById;
    }

    public void setCotacaosById(Collection<CotacaoEntity> cotacaosById) {
        this.cotacaosById = cotacaosById;
    }

    @OneToMany(mappedBy = "clienteByIdCliente")
    public Collection<ReservaEntity> getReservasById() {
        return reservasById;
    }

    public void setReservasById(Collection<ReservaEntity> reservasById) {
        this.reservasById = reservasById;
    }
}
