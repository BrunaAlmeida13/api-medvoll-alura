package med.voll.apimedvoll.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.apimedvoll.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,
        @NotBlank @Email(message = "{email.invalido}")
        String email,
        @NotBlank
        String telefone,
        @NotBlank @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", message = "{cpf.invalido}")
        String cpf,
        @NotNull @Valid
        DadosEndereco endereco
        ) {
}
