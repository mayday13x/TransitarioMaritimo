package pt.ipvc.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipvc.database.entity.FornecedorEntity;

@Repository
public interface FornecedorRepository  extends JpaRepository<FornecedorEntity, Integer> {
}
