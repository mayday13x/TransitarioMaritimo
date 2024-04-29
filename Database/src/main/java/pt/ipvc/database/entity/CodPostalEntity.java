package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "cod_postal", schema = "public", catalog = "transitario_maritimo")
public class CodPostalEntity {
    private int idCodPostal;
    private String localidade;
    private Collection<ClienteEntity> clientesByIdCodPostal;
    private Collection<FornecedorEntity> fornecedorsByIdCodPostal;
    private Collection<FuncionarioEntity> funcionariosByIdCodPostal;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cod_postal", nullable = false)
    public int getIdCodPostal() {
        return idCodPostal;
    }

    public void setIdCodPostal(int idCodPostal) {
        this.idCodPostal = idCodPostal;
    }

    @Basic
    @Column(name = "localidade", nullable = true, length = 255)
    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodPostalEntity that = (CodPostalEntity) o;

        if (idCodPostal != that.idCodPostal) return false;
        if (localidade != null ? !localidade.equals(that.localidade) : that.localidade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCodPostal;
        result = 31 * result + (localidade != null ? localidade.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "codPostalByIdCodPostal")
    public Collection<ClienteEntity> getClientesByIdCodPostal() {
        return clientesByIdCodPostal;
    }

    public void setClientesByIdCodPostal(Collection<ClienteEntity> clientesByIdCodPostal) {
        this.clientesByIdCodPostal = clientesByIdCodPostal;
    }

    @OneToMany(mappedBy = "codPostalByIdCodPostal")
    public Collection<FornecedorEntity> getFornecedorsByIdCodPostal() {
        return fornecedorsByIdCodPostal;
    }

    public void setFornecedorsByIdCodPostal(Collection<FornecedorEntity> fornecedorsByIdCodPostal) {
        this.fornecedorsByIdCodPostal = fornecedorsByIdCodPostal;
    }

    @OneToMany(mappedBy = "codPostalByIdCodPostal")
    public Collection<FuncionarioEntity> getFuncionariosByIdCodPostal() {
        return funcionariosByIdCodPostal;
    }

    public void setFuncionariosByIdCodPostal(Collection<FuncionarioEntity> funcionariosByIdCodPostal) {
        this.funcionariosByIdCodPostal = funcionariosByIdCodPostal;
    }
}
