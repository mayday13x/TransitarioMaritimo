package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "fornecedor", schema = "public", catalog = "transitario_maritimo")
public class FornecedorEntity {
    private Integer id;
    private String nome;
    private String rua;
    private Integer porta;
    private Integer idCodPostal;
    private Integer nif;
    private String telefone;
    private CodPostalEntity codPostalByIdCodPostal;
    private Collection<ServicoEntity> servicosById;

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
    @Column(name = "nif", nullable = true)
    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    @Basic
    @Column(name = "telefone", nullable = true, length = 20)
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FornecedorEntity that = (FornecedorEntity) o;

        if (id != that.id) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (rua != null ? !rua.equals(that.rua) : that.rua != null) return false;
        if (porta != null ? !porta.equals(that.porta) : that.porta != null) return false;
        if (idCodPostal != null ? !idCodPostal.equals(that.idCodPostal) : that.idCodPostal != null) return false;
        if (nif != null ? !nif.equals(that.nif) : that.nif != null) return false;
        if (telefone != null ? !telefone.equals(that.telefone) : that.telefone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (rua != null ? rua.hashCode() : 0);
        result = 31 * result + (porta != null ? porta.hashCode() : 0);
        result = 31 * result + (idCodPostal != null ? idCodPostal.hashCode() : 0);
        result = 31 * result + (nif != null ? nif.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_cod_postal", referencedColumnName = "id_cod_postal")
    public CodPostalEntity getCodPostalByIdCodPostal() {
        return codPostalByIdCodPostal;
    }

    public void setCodPostalByIdCodPostal(CodPostalEntity codPostalByIdCodPostal) {
        this.codPostalByIdCodPostal = codPostalByIdCodPostal;
    }

    @OneToMany(mappedBy = "fornecedorByIdFornecedor")
    public Collection<ServicoEntity> getServicosById() {
        return servicosById;
    }

    public void setServicosById(Collection<ServicoEntity> servicosById) {
        this.servicosById = servicosById;
    }
}
