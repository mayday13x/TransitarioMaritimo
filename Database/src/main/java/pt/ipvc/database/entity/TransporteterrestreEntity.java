package pt.ipvc.database.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "transporteterrestre", schema = "public", catalog = "transitario_maritimo")
public class TransporteterrestreEntity {
    private Integer id;
    private String matricula;
    private String origem;
    private String destino;
    private Collection<ServicoTransporteTEntity> servicoTransporteTSById;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "matricula", nullable = true, length = 255)
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Basic
    @Column(name = "origem", nullable = true, length = 255)
    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Basic
    @Column(name = "destino", nullable = true, length = 255)
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransporteterrestreEntity that = (TransporteterrestreEntity) o;

        if (id != that.id) return false;
        if (matricula != null ? !matricula.equals(that.matricula) : that.matricula != null) return false;
        if (origem != null ? !origem.equals(that.origem) : that.origem != null) return false;
        if (destino != null ? !destino.equals(that.destino) : that.destino != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (matricula != null ? matricula.hashCode() : 0);
        result = 31 * result + (origem != null ? origem.hashCode() : 0);
        result = 31 * result + (destino != null ? destino.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transporteterrestreByIdTransporteT")
    public Collection<ServicoTransporteTEntity> getServicoTransporteTSById() {
        return servicoTransporteTSById;
    }

    public void setServicoTransporteTSById(Collection<ServicoTransporteTEntity> servicoTransporteTSById) {
        this.servicoTransporteTSById = servicoTransporteTSById;
    }
}
