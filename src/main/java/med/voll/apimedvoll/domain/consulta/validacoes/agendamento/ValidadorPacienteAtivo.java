package med.voll.apimedvoll.domain.consulta.validacoes.agendamento;

import med.voll.apimedvoll.domain.ValidacaoException;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import med.voll.apimedvoll.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());

        if(!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído.");
        }
    }
}
