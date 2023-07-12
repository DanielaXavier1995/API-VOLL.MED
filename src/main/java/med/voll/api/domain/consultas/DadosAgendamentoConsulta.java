package med.voll.api.domain.consultas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        @Future //garantir uma data futura
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        Especialidade especialidade) {

}
