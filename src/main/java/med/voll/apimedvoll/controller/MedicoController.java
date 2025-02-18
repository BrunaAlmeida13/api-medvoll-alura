package med.voll.apimedvoll.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.apimedvoll.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("vollmed/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        repository.save(medico);

        //uri é o endereco da api
        var uri = uriBuilder.path("vollmed/api/medicos/medico/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("/listarMedicos")
    public ResponseEntity<Page<DadosListagemMedico>> listarTodosOsMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        /*
        O findAll() retorna uma lista de Médicos, então se faz o map para converter a lista de médicos para a lista do dto
        DadosListagemMedico
         */
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/atualizarMedico")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        /*
        o update é feito de forma automatica pelo jpa, basta carregar o médico referenciado pelo id,
        e sobrepor as informacoes
         */
    }

    @DeleteMapping("/inativarMedico/{id}")
    @Transactional
    public ResponseEntity inativarMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.desativarMedico();

        return ResponseEntity.noContent().build();
        /*
        noContent() não retorna ResponseEntity, noContent() cria o objeto e se chama o .build() para
        construir o objeto
         */
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity detalharMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
