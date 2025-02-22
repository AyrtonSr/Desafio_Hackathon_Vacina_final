package br.com.api.route;

import br.com.api.service.PacienteService;
import static spark.Spark.*;

public class PacienteRoutes {
    public static void configurarRotas() {
        path("/paciente", () -> {
            post("/inserir", PacienteService.inserir);
            put("/alterar/:id", PacienteService.alterar);
            delete("/excluir/:id", PacienteService.excluir);
            get("/consultar", PacienteService.consultarTodos);
            get("/consultar/:id", PacienteService.consultarPorId);
        });
    }
}
