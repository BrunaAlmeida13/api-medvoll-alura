package med.voll.apimedvoll.domain.paciente;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
