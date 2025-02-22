package br.com.api.route;

import br.com.api.service.ImunizacaoService;
import static spark.Spark.*;

public class ImunizacaoRoutes {

    public static void configurarRotas() {
        path("/imunizacao", () -> {
            post("/inserir", ImunizacaoService.inserir);
            put("/alterar/:id", ImunizacaoService.alterar);
            delete("/excluir/:id", ImunizacaoService.excluir);
            delete("/excluir/paciente/:id", ImunizacaoService.excluirPorPaciente);
            get("/consultar", ImunizacaoService.consultar);
            get("/consultar/:id", ImunizacaoService.consultarPorId);
            get("/consultar/paciente/:id", ImunizacaoService.consultarPorPaciente);
        });
    }
}
