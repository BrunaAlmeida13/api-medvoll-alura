package med.voll.apimedvoll.domain.consulta.validacoes.agendamento;

import med.voll.apimedvoll.domain.ValidacaoException;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaAgendamento")
/*
 * É uma classe de componente genérico (não é service, controller, etc) - apenas diz ao spring para carregar
 * na incialização do projeto
 */
public class ValidadorHorarioAntecedenciaConsulta implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();

        var diferencaEmMin = Duration.between(agora, dataConsulta).toMinutes();

        if(diferencaEmMin < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecendência mínima de 30 minutos.");
        }
    }
}
