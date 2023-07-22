package med.voll.api.domain.medico;

import med.voll.api.domain.consultas.ConsultaEntity;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.pacientes.DadosCadastroPaciente;
import med.voll.api.domain.pacientes.PacientesEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;
    @Test
    @DisplayName("Deveria devolver null qualdo o unico medico cadastrado não esta disponível na data")
    void medicoAleatorioCenario1() {
        //Pega a data atual e modifica para que seja a próxima segunda as 10 horas
         var proximaSegundaAs10 = LocalDate.now()
                 .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

         var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
         var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
         cadastrarConsulta(medico, paciente, proximaSegundaAs10);

         var medicoLivre = medicoRepository.medicoAleatorio(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

         assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico qualdo ele estiver disponível na data")
    void medicoAleatorioCenario2() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        //when ou act
        var medicoLivre = medicoRepository.medicoAleatorio(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(MedicoEntity medico, PacientesEntity paciente, LocalDateTime data) {
        em.persist(new ConsultaEntity(null, medico, paciente, data));
    }

    private MedicoEntity cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new MedicoEntity(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private PacientesEntity cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new PacientesEntity(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}