package med.voll.apimedvoll.controller;

import jakarta.validation.Valid;
import med.voll.apimedvoll.domain.consulta.AgendaDeConsultas;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import med.voll.apimedvoll.domain.consulta.DadosCancelamentoConsulta;
import med.voll.apimedvoll.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vollmed/api/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas service;

    @PostMapping("/agendamento")
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = service.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/cancelar-agendamento")
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
