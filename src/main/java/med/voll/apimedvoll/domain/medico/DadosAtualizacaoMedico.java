package med.voll.apimedvoll.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apimedvoll.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull //no dto de atualizacao, apenas o id é obrigatório
        Long id,
        String nome,
        String email,
        String telefone,
        DadosEndereco endereco
) {
}
