package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.pacientes.DadosAtualizacaoPacientes;
import med.voll.api.domain.pacientes.DadosCadastrosPacientes;
import med.voll.api.domain.pacientes.DadosDetalhamentoPacientes;
import med.voll.api.domain.pacientes.DadosListagemPacientes;
import med.voll.api.domain.pacientes.PacientesEntity;
import med.voll.api.domain.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacientesController {
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastroPacientes(@RequestBody @Valid DadosCadastrosPacientes dadosPacientes, UriComponentsBuilder uriBuilder) {

       var paciente = new PacientesEntity(dadosPacientes);

       pacientesRepository.save(paciente);

       var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

       return ResponseEntity.created(uri).body(new DadosDetalhamentoPacientes(paciente));

    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemPacientes>> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = pacientesRepository.findAll(paginacao).map(DadosListagemPacientes::new);
        return ResponseEntity.ok(page);
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPacientes dadosPacientes) {
        var pacientes = pacientesRepository.getReferenceById(dadosPacientes.id());
        pacientes.atualizarInformacoes(dadosPacientes);

        return ResponseEntity.ok(new DadosDetalhamentoPacientes(pacientes));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        pacientesRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar (@PathVariable Long id) {
        var paciente = pacientesRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPacientes(paciente));
    }
}
