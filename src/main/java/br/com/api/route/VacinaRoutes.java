package br.com.api.route;

import br.com.api.service.VacinaService;
import static spark.Spark.*;

public class VacinaRoutes {
    public static void configurarRotas() {
        path("/vacinas", () -> {
            get("/consultar", VacinaService.consultar);
            get("/consultar/faixa_etaria/:faixa", VacinaService.consultarPorFaixaEtaria);
            get("/consultar/idade_maior/:meses", VacinaService.consultarPorIdadeMaior);
        });
    }
}
