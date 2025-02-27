package med.voll.apimedvoll.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.apimedvoll.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Spring chama essa classe automaticamente
public class TratadorDeErros {

    //EntityNotFoundException - para o findById() - id não encontrado
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    //Recebe a exception que foi lançada para poder extrair dela informações para personalizar o tratamento do erro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();


        /* Fazer conversão de uma lista para a outra - erros.stream().map()
        * erros, me dê um stream, e mapeie cada objeto field erro para um objeto DadosErroValidacao (dto)
        */
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    public record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
