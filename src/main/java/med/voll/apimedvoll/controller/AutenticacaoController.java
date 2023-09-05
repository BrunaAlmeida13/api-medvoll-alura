package med.voll.apimedvoll.controller;

import jakarta.validation.Valid;
import med.voll.apimedvoll.domain.usuario.DadosAutenticadao;
import med.voll.apimedvoll.domain.usuario.Usuario;
import med.voll.apimedvoll.infra.security.DadosTokenJWT;
import med.voll.apimedvoll.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vollmed/api/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager; //classe do spring que irá chamar o service e iniciar o processo de autenticacao

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticadao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); //converte para o dto do spring
        var authentication = manager.authenticate(authenticationToken); //devolve um objeto que representa um usuário autenticado

        //.getPrincipal() - pegando o usuário logado
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
