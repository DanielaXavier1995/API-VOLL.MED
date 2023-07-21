package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

public interface Validador {
    void validar(DadosAgendamentoConsulta dados);
}
