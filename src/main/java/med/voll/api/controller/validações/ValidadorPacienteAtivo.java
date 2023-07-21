package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.pacientes.PacientesRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements Validador{
    @Autowired
    private PacientesRepository pacientesRepository;
    public void validar(DadosAgendamentoConsulta dados) {
        if(dados.idPaciente() == null) {
            return;
        }

        var pacienteAtivo = pacientesRepository.findAtivoById(dados.idPaciente());
        if(!pacienteAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada para pacientes inativos!");
        }
    }
}
