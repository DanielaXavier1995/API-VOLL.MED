package med.voll.api.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;
    public MedicoEntity(DadosCadastrosMedicos dadosMedicos) {
        this.ativo = true;
        this.nome = dadosMedicos.nome();
        this.email = dadosMedicos.email();
        this.telefone = dadosMedicos.telefone();
        this.crm = dadosMedicos.crm();
        this.especialidade = dadosMedicos.especialidade();
        this.endereco = new Endereco(dadosMedicos.endereco());
    }
    public void atualizarInformacoes(DadosAtualizacaoMedicos dadosMedicos) {
        if (dadosMedicos.nome() != null) {
            this.nome = dadosMedicos.nome();
        }
        if (dadosMedicos.telefone() != null) {
            this.telefone = dadosMedicos.telefone();
        }
        if (dadosMedicos.endereco() != null) {
            this.endereco.atualizarInformacoes(dadosMedicos.endereco());
        }
    }
    public void excluir() {
        this.ativo = false;
    }
}
