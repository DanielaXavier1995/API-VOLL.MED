package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.pacientes.DadosAtualizacaoPacientes;
import med.voll.api.pacientes.DadosCadastrosPacientes;
import med.voll.api.pacientes.DadosListagemPacientes;
import med.voll.api.pacientes.PacientesEntity;
import med.voll.api.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacientesController {
    @Autowired
    private PacientesRepository pacientesRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastroPacientes(@RequestBody @Valid DadosCadastrosPacientes dadosPacientes) {
        pacientesRepository.save(new PacientesEntity(dadosPacientes));
    }
    @GetMapping
    public Page<DadosListagemPacientes> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return pacientesRepository.findAll(paginacao).map(DadosListagemPacientes::new);
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPacientes dadosPacientes) {
        var pacientes = pacientesRepository.getReferenceById(dadosPacientes.id());
        pacientes.atualizarInformacoes(dadosPacientes);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        pacientesRepository.deleteById(id);
    }

}
