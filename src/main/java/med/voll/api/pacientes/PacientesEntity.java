package med.voll.api.pacientes;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PacientesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    public PacientesEntity(DadosCadastrosPacientes dadosPacientes) {
          this.nome = dadosPacientes.nome();
          this.email = dadosPacientes.email();
          this.telefone = dadosPacientes.telefone();
          this.cpf = dadosPacientes.cpf();
          this.endereco = new Endereco(dadosPacientes.endereco());
    }
}
