package med.voll.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastrosPacientes(
        @NotBlank(message = "O campo Nome é obrigatório")
        String nome,
        @NotBlank(message = "O campo Email é obrigatório")
        @Email(message = "O campo Email é inválido")
        String email,
        @NotBlank(message = "O campo Telefone é obrigatório")
        String telefone,
        @NotBlank(message = "O campo cpf é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "o campo cpf é inválido, deve conter 11 dígitos")
        String cpf,
        @NotNull
        @Valid
        DadosEndereco endereco) {
}
