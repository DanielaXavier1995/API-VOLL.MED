package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements Validador{
    @Autowired
    private MedicoRepository medicoRepository;
    public void validar (DadosAgendamentoConsulta dados) {

        if(dados.idMedico() == null) {
            return;
        }

        var medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if(!medicoAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada para médicos inativos");
        }
    }
}
