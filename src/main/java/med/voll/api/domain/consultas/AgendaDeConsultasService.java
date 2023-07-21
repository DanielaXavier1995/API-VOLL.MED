package med.voll.api.domain.consultas;

import med.voll.api.controller.validações.Validador;
import med.voll.api.domain.medico.MedicoEntity;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacientesRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultasService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private List<Validador> validadores;
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

            if (!pacientesRepository.existsById(dados.idPaciente())) {
                throw new ValidacaoException("O id do paciente informado não existe");
            }

            if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
                throw new ValidacaoException("O id do médico informado não existe");
            }

            //Chamando a lista de validações
            validadores.forEach(v -> v.validar(dados));

            //Buscando os dados do objeto medico e paciente no banco
            var medico = escolherMedico(dados);
            if (medico == null) {
                throw new ValidacaoException("Não existe médico disponível nessa data!");
            }
            var paciente = pacientesRepository.getReferenceById(dados.idPaciente());

            //atribuindo os dados ao objeto consulta
            var consulta = new ConsultaEntity(null, medico, paciente, dados.data());

            //Salvando o objeto consulta no banco
            consultaRepository.save(consulta);

            return new DadosDetalhamentoConsulta(consulta);
    }
    private MedicoEntity escolherMedico(DadosAgendamentoConsulta dados) {
        //Quando o id do médico não vem nulo:
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        //Quando o id do médico vier nulo:
        //Validar se a especialidade não está nula
        if(dados.especialidade() == null) {
            throw new ValidacaoException("Caso opte por não escolher um médico específico," +
                    " é necessário informar a especialidade!!");
        }
        //Montar Query no Repository filtrando médicos data e pela especialidade
        return medicoRepository.medicoAleatorio(dados.especialidade(), dados.data());
    }
}
