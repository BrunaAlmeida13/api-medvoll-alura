package med.voll.apimedvoll.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.apimedvoll.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") //application.properties
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            //algoritmo que irá gerar a assinatura
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            //criação do token
            return JWT.create()
                    .withIssuer("API voll.med")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId()) //info que será guardada dentro do token
                    .sign(algoritmo);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT, " + e);
        }
    }

    private Instant dataExpiracao() {
        //ZoneOffset.of("-03:00") - cria a data conforme o fuso do Brasil
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
