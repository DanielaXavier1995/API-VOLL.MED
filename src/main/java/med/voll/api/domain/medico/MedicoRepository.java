package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    Page<MedicoEntity> findAllByAtivoTrue(Pageable paginacao);
    @Query("""
            SELECT m FROM MedicoEntity m
            WHERE
            m.ativo = true
            AND
            m.id NOT IN
               (SELECT c.idMedico.id FROM ConsultaEntity c
               WHERE
               c.data = :data)
            AND
            m.especialidade = :especialidade
            ORDER BY rand()
            LIMIT 1
            """)
    MedicoEntity medicoAleatorio(Especialidade especialidade, LocalDateTime data);
    @Query("""
            SELECT m.ativo 
            FROM MedicoEntity m
            WHERE 
            m.id = :id
            """)
    Boolean findAtivoById(Long id);
}

