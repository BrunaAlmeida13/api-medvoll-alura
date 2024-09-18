package med.voll.apimedvoll.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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

    private static String ISSUER = "API Voll.med";

    public String gerarToken(Usuario usuario) {
        try {
            //algoritmo que irá gerar a assinatura
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            //criação do token
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId()) //info que será guardada dentro do token
                    .sign(algoritmo);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT, " + e);
        }
    }

    //Verifica se o Token está válido e devolve o usuário (subject) que está armazenado dentro do token
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!" + tokenJWT);
        }
    }

    private Instant dataExpiracao() {
        //ZoneOffset.of("-03:00") - cria a data conforme o fuso do Brasil
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
