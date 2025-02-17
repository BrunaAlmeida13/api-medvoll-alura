package med.voll.apimedvoll.domain.consulta.validacoes.agendamento;

import med.voll.apimedvoll.domain.ValidacaoException;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import med.voll.apimedvoll.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        //Escolha do médico é opcional
        if(dados.idMedico() == null) {
            return;
        }

        var medicoAtivo = repository.findAtivoById(dados.idMedico()).orElse(false);;
        if(!medicoAtivo) {
            throw new ValidacaoException("Consulta deve ser agendada com médico ativo.");
        }
    }
}
