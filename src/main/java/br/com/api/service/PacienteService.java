package br.com.api.service;

import br.com.api.dao.PacienteDAO;
import br.com.api.dto.PacienteDTO;
import br.com.api.model.Paciente;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteService {
    private static final PacienteDAO pacienteDAO = new PacienteDAO();
    private static final ObjectMapper jsonConverter = new ObjectMapper();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Route inserir = (Request request, Response response) -> {
        try {
            PacienteDTO pacienteDTO = jsonConverter.readValue(request.body(), PacienteDTO.class);

            if (pacienteDTO.getNome() == null || pacienteDTO.getNome().isEmpty()) {
                response.status(400);
                return "{\"message\": \"Nome do paciente é obrigatório.\"}";
            }

            Date dataNascimento = dateFormat.parse(pacienteDTO.getDataNascimento());

            Paciente paciente = new Paciente(0, pacienteDTO.getNome(), pacienteDTO.getCpf(), pacienteDTO.getSexo(), dataNascimento);
            pacienteDAO.inserir(paciente);

            response.status(201);
            return "{\"message\": \"Paciente cadastrado com sucesso, ID: " + paciente.getId() + "\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao cadastrar paciente: " + e.getMessage() + "\"}";
        }
    };

    public static Route alterar = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(request.params(":id"));
            Paciente pacienteExistente = pacienteDAO.buscarPorId(id);

            if (pacienteExistente == null) {
                response.status(404);
                return "{\"message\": \"Paciente não encontrado.\"}";
            }

            PacienteDTO pacienteDTO = jsonConverter.readValue(request.body(), PacienteDTO.class);

            if (pacienteDTO.getNome() == null || pacienteDTO.getNome().isEmpty()) {
                response.status(400);
                return "{\"message\": \"Nome do paciente é obrigatório.\"}";
            }

            Date dataNascimento = dateFormat.parse(pacienteDTO.getDataNascimento());

            pacienteExistente.setNome(pacienteDTO.getNome());
            pacienteExistente.setCpf(pacienteDTO.getCpf());
            pacienteExistente.setSexo(pacienteDTO.getSexo());
            pacienteExistente.setDataNascimento(dataNascimento);

            pacienteDAO.atualizar(pacienteExistente);

            response.status(200);
            return "{\"message\": \"Paciente atualizado com sucesso.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao atualizar paciente: " + e.getMessage() + "\"}";
        }
    };

    public static Route excluir = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(request.params(":id"));

            Paciente paciente = pacienteDAO.buscarPorId(id);
            if (paciente == null) {
                response.status(404);
                return "{\"message\": \"Paciente não encontrado.\"}";
            }

            pacienteDAO.deletar(id);
            response.status(200);
            return "{\"message\": \"Paciente excluído com sucesso.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao excluir paciente: " + e.getMessage() + "\"}";
        }
    };

    public static Route consultarTodos = (Request request, Response response) -> {
        try {
            List<Paciente> pacientes = pacienteDAO.listarTodos();

            // Converter Lista de Pacientes → Lista de PacienteDTO
            List<PacienteDTO> pacientesDTO = pacientes.stream().map(paciente ->
                    new PacienteDTO(
                            paciente.getId(),
                            paciente.getNome(),
                            paciente.getCpf(),
                            paciente.getSexo(),
                            dateFormat.format(paciente.getDataNascimento()) // Formatando data
                    )
            ).collect(Collectors.toList());

            response.status(200);
            return jsonConverter.writeValueAsString(pacientesDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar pacientes: " + e.getMessage() + "\"}";
        }
    };

    public static Route consultarPorId = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(request.params(":id"));
            Paciente paciente = pacienteDAO.buscarPorId(id);

            if (paciente == null) {
                response.status(404);
                return "{\"message\": \"Paciente não encontrado.\"}";
            }

            // Converter Paciente → PacienteDTO
            PacienteDTO pacienteDTO = new PacienteDTO(
                    paciente.getId(),
                    paciente.getNome(),
                    paciente.getCpf(),
                    paciente.getSexo(),
                    dateFormat.format(paciente.getDataNascimento()) // Formatando data
            );

            response.status(200);
            return jsonConverter.writeValueAsString(pacienteDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar paciente: " + e.getMessage() + "\"}";
        }
    };

}
