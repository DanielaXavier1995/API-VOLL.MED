package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedComConsultaMesmoHorario implements Validador{
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar (DadosAgendamentoConsulta dados) {
        var medico = consultaRepository.existsByIdMedicoIdAndData(dados.idMedico(), dados.data());

        if(medico) {
            throw new ValidacaoException("Médico já possui consulta agendada nesse dia e horário!");
        }
    }
}
