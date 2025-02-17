package med.voll.apimedvoll.domain.consulta.validacoes.cancelamento;

import med.voll.apimedvoll.domain.ValidacaoException;
import med.voll.apimedvoll.domain.consulta.ConsultaRepository;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import med.voll.apimedvoll.domain.consulta.DadosCancelamentoConsulta;
import med.voll.apimedvoll.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
/*
 * É uma classe de componente genérico (não é service, controller, etc) - apenas diz ao spring para carregar
 * na incialização do projeto
 */
public class ValidadorHorarioAntecedenciaConsulta implements ValidadorCancelamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
