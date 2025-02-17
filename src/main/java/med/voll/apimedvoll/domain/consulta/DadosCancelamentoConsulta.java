package med.voll.apimedvoll.domain.consulta;

import jakarta.validation.constraints.NotNull;
import med.voll.apimedvoll.domain.consulta.enums.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
) {
}
