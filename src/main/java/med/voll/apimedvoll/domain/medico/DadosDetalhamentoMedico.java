package med.voll.apimedvoll.domain.medico;

import med.voll.apimedvoll.domain.endereco.Endereco;
import med.voll.apimedvoll.domain.medico.enums.EspecialidadeEnum;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone,
                                      EspecialidadeEnum especialidade,
                                      Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(),
                medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
