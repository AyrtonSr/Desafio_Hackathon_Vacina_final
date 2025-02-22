package br.com.api.service;

import br.com.api.dao.VacinaDAO;
import br.com.api.dto.VacinaDTO;
import br.com.api.model.Vacina;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.List;

import java.util.stream.Collectors;

public class VacinaService {
    private static final VacinaDAO vacinaDAO = new VacinaDAO();
    private static final ObjectMapper jsonConverter = new ObjectMapper();

    public static Route consultar = (Request request, Response response) -> {
        try {
            List<Vacina> vacinas = vacinaDAO.listarTodos();

            List<VacinaDTO> vacinasDTO = vacinas.stream().map(vacina ->
                    new VacinaDTO(
                            vacina.getId(),
                            vacina.getNome(),
                            vacina.getDescricao(),
                            vacina.getLimiteIdadeMeses(),
                            vacina.getPublicoAlvo().stream().map(Enum::name).collect(Collectors.toList()) // Converte ENUM para List<String>
                    )
            ).collect(Collectors.toList());

            response.status(200);
            return jsonConverter.writeValueAsString(vacinasDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar vacinas: " + e.getMessage() + "\"}";
        }
    };

    public static Route consultarPorFaixaEtaria = (Request request, Response response) -> {
        try {
            String faixa = request.params(":faixa").toUpperCase();

            if (!faixa.equals("CRIANCA") && !faixa.equals("ADOLESCENTE") && !faixa.equals("ADULTO") && !faixa.equals("GESTANTE")) {
                response.status(400);
                return "{\"message\": \"Faixa etária inválida. Use CRIANCA, ADOLESCENTE, ADULTO ou GESTANTE.\"}";
            }

            List<Vacina> vacinas = vacinaDAO.buscarPorFaixaEtaria(faixa);

            List<VacinaDTO> vacinasDTO = vacinas.stream().map(vacina ->
                    new VacinaDTO(
                            vacina.getId(),
                            vacina.getNome(),
                            vacina.getDescricao(),
                            vacina.getLimiteIdadeMeses(),
                            vacina.getPublicoAlvo().stream().map(Enum::name).collect(Collectors.toList())
                    )
            ).collect(Collectors.toList());

            response.status(200);
            return jsonConverter.writeValueAsString(vacinasDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar vacinas por faixa etária: " + e.getMessage() + "\"}";
        }
    };

    public static Route consultarPorIdadeMaior = (Request request, Response response) -> {
        try {
            int meses = Integer.parseInt(request.params(":meses"));

            List<Vacina> vacinas = vacinaDAO.buscarPorIdadeMaior(meses);

            List<VacinaDTO> vacinasDTO = vacinas.stream().map(vacina ->
                    new VacinaDTO(
                            vacina.getId(),
                            vacina.getNome(),
                            vacina.getDescricao(),
                            vacina.getLimiteIdadeMeses(),
                            vacina.getPublicoAlvo().stream().map(Enum::name).collect(Collectors.toList())
                    )
            ).collect(Collectors.toList());

            response.status(200);
            return jsonConverter.writeValueAsString(vacinasDTO);
        } catch (NumberFormatException e) {
            response.status(400);
            return "{\"message\": \"O valor fornecido para meses deve ser um número inteiro.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar vacinas por idade: " + e.getMessage() + "\"}";
        }
    };
}
