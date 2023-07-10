package med.voll.api.domain.pacientes;

import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoPacientes(String nome, String email, String telefone, String cpf, Endereco endereco) {
    public DadosDetalhamentoPacientes(PacientesEntity pacientes) {
        this(pacientes.getNome(), pacientes.getEmail(), pacientes.getTelefone(), pacientes.getCpf(), pacientes.getEndereco());
    }
}
