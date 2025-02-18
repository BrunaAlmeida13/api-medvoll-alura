package med.voll.apimedvoll.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.apimedvoll.domain.consulta.AgendaDeConsultas;
import med.voll.apimedvoll.domain.consulta.DadosAgendamentoConsulta;
import med.voll.apimedvoll.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearer-key")
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
