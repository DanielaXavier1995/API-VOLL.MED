package med.voll.api.domain.pacientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface PacientesRepository extends JpaRepository <PacientesEntity, Long> {
    @Query("""
            SELECT p.ativo 
            FROM PacientesEntity p
            WHERE
            p.id = :id
            """)
    Boolean findAtivoById(Long id);
}
