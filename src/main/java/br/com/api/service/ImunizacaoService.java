package br.com.api.service;

import br.com.api.dao.ImunizacaoDAO;
import br.com.api.dto.ImunizacaoDTO;
import br.com.api.model.Imunizacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ImunizacaoService {

    private static final ImunizacaoDAO imunizacaoDAO = new ImunizacaoDAO();
    private static final ObjectMapper jsonConverter = new ObjectMapper();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Route inserir = (Request request, Response response) -> {
        try {
            ImunizacaoDTO imunizacaoDTO = jsonConverter.readValue(request.body(), ImunizacaoDTO.class);

            if (imunizacaoDTO.getDataAplicacao() == null || imunizacaoDTO.getDataAplicacao().isEmpty()) {
                response.status(400);
                return "{\"message\": \"Data de aplicação é obrigatória.\"}";
            }

            Date dataAplicacao = dateFormat.parse(imunizacaoDTO.getDataAplicacao());

            Imunizacao imunizacao = new Imunizacao(
                    0,
                    dataAplicacao,
                    imunizacaoDTO.getFabricante(),
                    imunizacaoDTO.getLote(),
                    imunizacaoDTO.getLocalAplicacao(),
                    imunizacaoDTO.getProfissionalAplicador(),
                    imunizacaoDTO.getIdPaciente(),
                    imunizacaoDTO.getIdDose()
            );

            imunizacaoDAO.inserir(imunizacao);

            response.status(201);
            return "{\"message\": \"Imunização registrada com sucesso, ID: " + imunizacao.getId() + "\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao registrar imunização: " + e.getMessage() + "\"}";
        }
    };

    public static Route alterar = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(request.params(":id"));
            Imunizacao imunizacao = imunizacaoDAO.buscarPorId(id);

            if (imunizacao == null) {
                response.status(404);
                return "{\"message\": \"Imunização não encontrada.\"}";
            }

            String fabricante = request.queryParams("fabricante");
            String lote = request.queryParams("lote");
            String localAplicacao = request.queryParams("localAplicacao");
            String profissionalAplicador = request.queryParams("profissionalAplicador");
            Date dataAplicacao = dateFormat.parse(request.queryParams("dataAplicacao"));

            imunizacao.setFabricante(fabricante);
            imunizacao.setLote(lote);
            imunizacao.setLocalAplicacao(localAplicacao);
            imunizacao.setProfissionalAplicador(profissionalAplicador);
            imunizacao.setDataAplicacao(dataAplicacao);

            imunizacaoDAO.atualizar(imunizacao);

            response.status(200);
            return "{\"message\": \"Imunização atualizada com sucesso.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao atualizar imunização: " + e.getMessage() + "\"}";
        }
    };

    public static Route excluir = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(request.params(":id"));
            imunizacaoDAO.deletar(id);
            response.status(200);
            return "{\"message\": \"Imunização excluída com sucesso.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao excluir imunização: " + e.getMessage() + "\"}";
        }
    };

    public static Route excluirPorPaciente = (Request request, Response response) -> {
        try {
            int idPaciente = Integer.parseInt(request.params(":id"));
            imunizacaoDAO.deletarPorPaciente(idPaciente);
            response.status(200);
            return "{\"message\": \"Todas as imunizações do paciente foram excluídas.\"}";
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao excluir imunizações do paciente: " + e.getMessage() + "\"}";
        }
    };

    public static Route consultar = (Request request, Response response) -> {
        try {
            List<Imunizacao> imunizacoes = imunizacaoDAO.listarTodas();

            List<ImunizacaoDTO> imunizacoesDTO = imunizacoes.stream().map(imunizacao ->
                    new ImunizacaoDTO(
                            imunizacao.getId(),
                            dateFormat.format(imunizacao.getDataAplicacao()),
                            imunizacao.getFabricante(),
                            imunizacao.getLote(),
                            imunizacao.getLocalAplicacao(),
                            imunizacao.getProfissionalAplicador(),
                            imunizacao.getIdPaciente(),
                            imunizacao.getIdDose()
                    )
            ).collect(Collectors.toList());

            response.status(200);
            return jsonConverter.writeValueAsString(imunizacoesDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar imunizações: " + e.getMessage() + "\"}";
        }
    };


    public static Route consultarPorId = (Request request, Response response) -> {
        try {
            int id = Integer.parseInt(request.params(":id"));
            Imunizacao imunizacao = imunizacaoDAO.buscarPorId(id);

            if (imunizacao == null) {
                response.status(404);
                return "{\"message\": \"Imunização não encontrada.\"}";
            }

            ImunizacaoDTO imunizacaoDTO = new ImunizacaoDTO(
                    imunizacao.getId(),
                    dateFormat.format(imunizacao.getDataAplicacao()),
                    imunizacao.getFabricante(),
                    imunizacao.getLote(),
                    imunizacao.getLocalAplicacao(),
                    imunizacao.getProfissionalAplicador(),
                    imunizacao.getIdPaciente(),
                    imunizacao.getIdDose()
            );

            response.status(200);
            return jsonConverter.writeValueAsString(imunizacaoDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar imunização: " + e.getMessage() + "\"}";
        }
    };

    public static Route consultarPorPaciente = (Request request, Response response) -> {
        try {
            int idPaciente = Integer.parseInt(request.params(":id"));
            List<Imunizacao> imunizacoes = imunizacaoDAO.listarPorPaciente(idPaciente);

            List<ImunizacaoDTO> imunizacoesDTO = imunizacoes.stream().map(imunizacao ->
                    new ImunizacaoDTO(
                            imunizacao.getId(),
                            dateFormat.format(imunizacao.getDataAplicacao()),
                            imunizacao.getFabricante(),
                            imunizacao.getLote(),
                            imunizacao.getLocalAplicacao(),
                            imunizacao.getProfissionalAplicador(),
                            imunizacao.getIdPaciente(),
                            imunizacao.getIdDose()
                    )
            ).collect(Collectors.toList());

            response.status(200);
            return jsonConverter.writeValueAsString(imunizacoesDTO);
        } catch (Exception e) {
            response.status(500);
            return "{\"message\": \"Erro ao buscar imunizações do paciente: " + e.getMessage() + "\"}";
        }
    };
}
