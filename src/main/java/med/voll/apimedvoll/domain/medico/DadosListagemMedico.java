package med.voll.apimedvoll.domain.medico;

import med.voll.apimedvoll.domain.medico.enums.EspecialidadeEnum;

public record DadosListagemMedico(
        Long id,
        String nome,
        String email,
        String crm,
        EspecialidadeEnum especialidade
        ) {

        /*
        this() -> chamando o próprio construtor do record
        pode-se ter mais construtores no record, mas ele precisa chamar o construtor principal que ta declarado na
        assinatura do record, passando cada um dos campos que foram definidos
         */
        public DadosListagemMedico(Medico medico) {
                this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
        }
}
