package med.voll.apimedvoll.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.apimedvoll.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {}
