package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank(message = "O campo Nome é obrigatório")
        String nome,
        @NotBlank(message = "O campo Email é obrigatório")
        @Email(message = "formato do Email inválido")
        String email,
        @NotBlank(message = "O campo Telefone é obrigatório")
        String telefone,
        @NotBlank(message = "O campo crm é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "formato do crm inválido, deve conter de 4 a 6 dígitos")
        String crm,
        @NotNull(message = "O campo Especialidade não pode ser nulo")
        Especialidade especialidade,
        @NotNull
        @Valid
        DadosEndereco endereco) {
}
