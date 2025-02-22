package br.com.api.service;

import br.com.api.dao.ImunizacaoDAO;
import br.com.api.dao.DoseDAO;
import br.com.api.dao.VacinaDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class EstatisticasService {
    private static final ImunizacaoDAO imunizacaoDAO = new ImunizacaoDAO();
    private static final DoseDAO doseDAO = new DoseDAO();
    private static final VacinaDAO vacinaDAO = new VacinaDAO();

    public static Route imunizacoesPorPaciente = (Request request, Response response) -> {
        try {
            int idPaciente = Integer.parseInt(request.params(":id"));
            int quantidade = imunizacaoDAO.listarPorPaciente(idPaciente).size();

            response.status(200);
            return "{\"quantidade_imunizacoes\": " + quantidade + "}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao contar imunizações do paciente: " + e.getMessage() + "\"}";
        }
    };


    public static Route proximasImunizacoesPorPaciente = (Request request, Response response) -> {
        try {
            int idPaciente = Integer.parseInt(request.params(":id"));
            int quantidade = doseDAO.listarPorVacina(idPaciente).size();

            response.status(200);
            return "{\"quantidade_proximas_imunizacoes\": " + quantidade + "}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao contar próximas imunizações do paciente: " + e.getMessage() + "\"}";
        }
    };

    public static Route imunizacoesAtrasadasPorPaciente = (Request request, Response response) -> {
        try {
            int idPaciente = Integer.parseInt(request.params(":id"));
            int quantidade = 0;

            response.status(200);
            return "{\"quantidade_imunizacoes_atrasadas\": " + quantidade + "}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao contar imunizações atrasadas do paciente: " + e.getMessage() + "\"}";
        }
    };

    public static Route imunizacoesPorIdadeMaior = (Request request, Response response) -> {
        try {
            int meses = Integer.parseInt(request.params(":meses"));
            int quantidade = vacinaDAO.buscarPorIdadeMaior(meses).size();

            response.status(200);
            return "{\"quantidade_imunizacoes_idade_maior\": " + quantidade + "}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao contar imunizações por idade maior: " + e.getMessage() + "\"}";
        }
    };
}
