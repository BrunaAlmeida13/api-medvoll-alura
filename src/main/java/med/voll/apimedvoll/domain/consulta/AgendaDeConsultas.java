package med.voll.apimedvoll.domain.consulta;

import med.voll.apimedvoll.domain.ValidacaoException;
import med.voll.apimedvoll.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.apimedvoll.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.apimedvoll.domain.medico.Medico;
import med.voll.apimedvoll.domain.medico.MedicoRepository;
import med.voll.apimedvoll.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    /*
     * O Spring automaticamente detecta que uma lista está sendo injetada, e o generics <> é uma interface, então ele
     * procura todas as classes que implementa essa interface e as coloca nessa lista.
     */
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        checagemPacienteEMedicoExiste(dados);

        /* S.O.L.I.D > S - O - D
         * Single Responsability Principle: Cada classe de validação tem apenas uma única responsabilidade, executa apenas uma RN.
         * Open-Closed Principle: A classe está 'fechada' para modificação e aberta para extensão (adicionar novos validadores).
         * Dependency Inversion Principle: Essa classe service depende de uma abstração, que é a interface, e não depende dos validadores
         * (então a service, que é um módulo de alto nível, não depende dos validadores que são módulos de baixo nível).
         */
        validadores.forEach(v -> v.validar(dados)); // Design Pattern > "Strategy"

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedicoSeIdNull(dados);
        if(medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data.");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        repository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!repository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = repository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedicoSeIdNull(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

         return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    private void checagemPacienteEMedicoExiste(DadosAgendamentoConsulta dados) {
        if(!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do Paciente infomado não existe!");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do Médico infomado não existe!");
        }
    }
}
