package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.pacientes.DadosCadastrosPacientes;
import med.voll.api.pacientes.PacientesEntity;
import med.voll.api.pacientes.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacientesController {
    @Autowired
    private PacientesRepository pacientesRepository;
    @PostMapping
    @Transactional
    public void cadastroPacientes(@RequestBody @Valid DadosCadastrosPacientes dadosPacientes) {
        pacientesRepository.save(new PacientesEntity(dadosPacientes));
    }
}
