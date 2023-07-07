package med.voll.api.controller;

import med.voll.api.pacientes.DadosCadastrosPacientes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacientesController {
    @PostMapping
    public void cadastroPacientes(@RequestBody DadosCadastrosPacientes dadosPacientes) {
        System.out.println(dadosPacientes);
    }
}
