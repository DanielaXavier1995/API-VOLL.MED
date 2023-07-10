package med.voll.api.domain.pacientes;

public record DadosListagemPacientes(Long id, String nome, String email, String telefone, String cpf) {
    public DadosListagemPacientes(PacientesEntity pacientes) {
        this(pacientes.getId(), pacientes.getNome(), pacientes.getEmail(), pacientes.getTelefone(), pacientes.getCpf());
    }
}
