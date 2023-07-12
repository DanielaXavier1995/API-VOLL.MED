package med.voll.api.domain.consultas;

import med.voll.api.domain.medico.MedicoEntity;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultasService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacientesRepository pacientesRepository;
    public void agendar(DadosAgendamentoConsulta dados) {

        //Regras de negócio:
        if(!pacientesRepository.existsById(dados.idPaciente())){
            throw new RuntimeException("O id do paciente informado não existe");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new RuntimeException("O id do médico informado não existe");
        }

        //Buscando os dados do objeto medico e paciente no banco
        var medico = escolherMedico(dados);
        var paciente = pacientesRepository.getReferenceById(dados.idPaciente());

        //atribuindo os dados ao objeto consulta
        var consulta = new ConsultaEntity(null, medico, paciente, dados.data());

        //Salvando o objeto consulta no banco
        consultaRepository.save(consulta);
    }
    private MedicoEntity escolherMedico(DadosAgendamentoConsulta dados) {
        //Quando o id do médico não vem nulo:
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        //Quando o id do médico vier nulo:
        //Validar se a especialidade não está nula
        if(dados.especialidade() == null) {
            throw new RuntimeException("Caso opte por não escolher um médico específico," +
                    " é necessário informar a especialidade!!");
        }

        //Montar Query no Repository filtrando médicos pela data e pela especialidade
        return medicoRepository.medicoAleatório(dados.especialidade(), dados.data());
    }
}
