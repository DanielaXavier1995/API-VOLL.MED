package med.voll.api.controller;

import med.voll.api.domain.consultas.AgendaDeConsultasService;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJSON;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJSON;
    @MockBean //ao injetar o service no teste converta para Mock
    private AgendaDeConsultasService agendaDeConsultasService;
    @Test
    @DisplayName("Deveria devolver código http 400 quando as informações estão inválidas")
    @WithMockUser //Simulando um usuário logado na app
    void agendarCenario01() throws Exception {
        //performa uma requisição
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deveria devolver código http 200 quando as informações estão válidas")
    @WithMockUser //Simulando um usuário logado na app
    void agendarCenario02() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 1l, data);
        when(agendaDeConsultasService.agendar(any())).thenReturn(dadosDetalhamento);

        //performa uma requisição
        var response = mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON) //cabeçalho content type informando que se trata de um JSON
                        .content(dadosAgendamentoConsultaJSON.write(
                                new DadosAgendamentoConsulta(2l, 1l, data, especialidade)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJSON.write(dadosDetalhamento).getJson();

        //conteúdo do corpo da resposta como String
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}