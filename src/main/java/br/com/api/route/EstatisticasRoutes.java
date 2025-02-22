package br.com.api.route;

import br.com.api.service.EstatisticasService;
import static spark.Spark.*;

public class EstatisticasRoutes {
    public static void configurarRotas() {
        path("/estatisticas", () -> {
            get("/imunizacoes/paciente/:id", EstatisticasService.imunizacoesPorPaciente);
            get("/proximas_imunizacoes/paciente/:id", EstatisticasService.proximasImunizacoesPorPaciente);
            get("/imunizacoes_atrasadas/paciente/:id", EstatisticasService.imunizacoesAtrasadasPorPaciente);
            get("/imunizacoes/idade_maior/:meses", EstatisticasService.imunizacoesPorIdadeMaior);
        });
    }
}
