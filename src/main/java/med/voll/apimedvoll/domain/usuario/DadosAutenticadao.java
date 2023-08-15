package med.voll.apimedvoll.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAutenticadao(

        String login,

        String senha
) {
}
