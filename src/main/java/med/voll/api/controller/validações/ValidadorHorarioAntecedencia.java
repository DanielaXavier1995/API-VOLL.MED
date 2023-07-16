package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta). toMinutes();

        if(diferencaEmMinutos < 30) {
            throw new RuntimeException("Consulta deve ser agendada com antecedência mínima de 30 minutos!");
        }
    }
}
