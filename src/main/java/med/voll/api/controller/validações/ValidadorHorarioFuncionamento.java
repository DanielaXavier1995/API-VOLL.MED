package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncionamento {
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        //Checar se é domingo
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        //Fora do horário de abertura
        var antesDaAbertura = dataConsulta.getHour() < 7;
        //Fora do horário de fechamento (levando em consideração que a ultima consulta começa
        //as 18 hrs e não pode ser marcada consulta após esse horário:
        var depoisDoFechamento = dataConsulta.getHour() > 18;

        if(domingo || antesDaAbertura || depoisDoFechamento) {
            throw new RuntimeException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
