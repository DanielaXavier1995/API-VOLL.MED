package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastrosMedicos;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.MedicoEntity;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    @Transactional
    public void cadastroMedicos(@RequestBody @Valid DadosCadastrosMedicos dadosMedicos) {
        medicoRepository.save(new MedicoEntity(dadosMedicos));
    }
    @GetMapping
    public List<DadosListagemMedico> listarMedicos() {
      return medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
    }
}
