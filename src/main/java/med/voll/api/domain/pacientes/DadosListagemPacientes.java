package med.voll.api.pacientes;

import med.voll.api.medico.MedicoEntity;

public record DadosListagemPacientes(Long id, String nome, String email, String telefone, String cpf) {
    public DadosListagemPacientes(PacientesEntity pacientes) {
        this(pacientes.getId(), pacientes.getNome(), pacientes.getEmail(), pacientes.getTelefone(), pacientes.getCpf());
    }
}
