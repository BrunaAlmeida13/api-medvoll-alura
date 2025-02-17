package med.voll.apimedvoll.domain.consulta.validacoes.agendamento;

import med.voll.apimedvoll.domain.ValidacaoException;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var hrAntesAberturaClinica = dataConsulta.getHour() < 7;
        var hrDepoisEncerramentoClinica = dataConsulta.getHour() > 18;

        if(domingo || hrAntesAberturaClinica || hrDepoisEncerramentoClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica!");
        }
    }
}
