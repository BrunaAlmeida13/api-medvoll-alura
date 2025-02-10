package med.voll.apimedvoll.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/vollmed/api/login").permitAll() //Qualquer requisição POST para o endpoint /login
                // é permitida sem necessidade de autenticação
                .anyRequest().authenticated() //Essa linha garante que todas as outras requisições, exceto o /login, exijam que o usuário esteja autenticado.
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //setta as ordens dos filtros (primeiro o meu filtro, depois o do spring)
                .build();
    }


    //Método para o spring conseguir injetar a classe AuthenticationManager no controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //Esse é o algoritmo de hash de senha
    }
}

/*
csrf().disable() - para desabilitar a proteção contra ataques do tipo Cross-Site Request Forgery, por que o token já é
uma proteção contra ataques csrf
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) - desabilitando a sessão statefull (formulário
de login padrão do spring security)
 */