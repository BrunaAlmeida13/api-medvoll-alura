package med.voll.apimedvoll.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.apimedvoll.domain.endereco.DadosEndereco;
import med.voll.apimedvoll.domain.medico.enums.EspecialidadeEnum;

public record DadosCadastroMedico(
        @NotNull @NotBlank
        String nome,
        @NotBlank @Email(message = "{email.invalido}")
        String email,
        @NotBlank
        String telefone,
        @NotBlank @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}") //d - digitos, min 4 max 6
        String crm,
        @NotNull
        EspecialidadeEnum especialidade,
        @NotNull @Valid
        DadosEndereco endereco) {
}
