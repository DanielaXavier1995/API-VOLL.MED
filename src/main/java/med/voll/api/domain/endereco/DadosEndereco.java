package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank(message = "O campo Logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "O campo bairro é obrigatório")
        String bairro,
        @NotBlank(message = "O campo cep é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "campo cep inválido, deve conter 8 digítos")
        String cep,
        @NotBlank(message = "O campo cidade é obrigatório")
        String cidade,
        @NotBlank(message = "O campo uf é obrigatório")
        String uf,
        String complemento,
        String numero) {
}
