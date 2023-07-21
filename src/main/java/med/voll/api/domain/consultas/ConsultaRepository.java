package med.voll.api.domain.consultas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    Boolean existsByIdMedicoIdAndData(Long idMedico, LocalDateTime data);
    Boolean existsByIdPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

}
