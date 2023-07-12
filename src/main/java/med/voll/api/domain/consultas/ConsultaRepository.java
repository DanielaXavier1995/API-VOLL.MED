package med.voll.api.domain.consultas;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {

}
