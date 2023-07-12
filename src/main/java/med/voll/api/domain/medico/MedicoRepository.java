package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    Page<MedicoEntity> findAllByAtivoTrue(Pageable paginacao);
    @Query("""
            SELECT m FROM medicos m
            WHERE
            m.ativo = 1
            AND
            m.id not in
               (SELECT c.medico_id FROM consultas c
               WHERE
               c.data = :data) 
            AND
            m.especialidade = :especialidade
            ORDER BY rand()
            LIMIT 1
            """)
    MedicoEntity medicoAleatorio(Especialidade especialidade, LocalDateTime data);
}
