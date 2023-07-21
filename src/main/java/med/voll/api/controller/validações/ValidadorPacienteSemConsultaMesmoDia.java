package med.voll.api.controller.validações;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemConsultaMesmoDia implements Validador{
      @Autowired
      private ConsultaRepository consultaRepository;
      public void validar (DadosAgendamentoConsulta dados) {
           var primeiroHorario = dados.data().withHour(7);
           var ultimoHorario = dados.data().withHour(18);

           var paciente = consultaRepository.existsByIdPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

           if(paciente) {
               throw new ValidacaoException("Paciente já possui consulta agendada nessa data!");
           }

      }
}
