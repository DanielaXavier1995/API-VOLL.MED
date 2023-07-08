package med.voll.api.pacientes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacientesRepository extends JpaRepository <PacientesEntity, Long> {
}
