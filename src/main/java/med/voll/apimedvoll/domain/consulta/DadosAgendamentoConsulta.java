package med.voll.apimedvoll.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.apimedvoll.domain.medico.enums.EspecialidadeEnum;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        EspecialidadeEnum especialidade,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future //Regra para não aceitar datas anteriores ao dia atual
        LocalDateTime data
) {
}
