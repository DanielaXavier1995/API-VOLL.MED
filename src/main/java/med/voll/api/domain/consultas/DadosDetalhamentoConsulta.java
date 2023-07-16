package med.voll.api.domain.consultas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long idConsulta,
        Long idMedico,
        Long idPaciente,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data) {
}
