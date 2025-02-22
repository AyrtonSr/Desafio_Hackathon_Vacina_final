package br.com.api;

import spark.Spark;
import java.sql.Connection;
import br.com.api.config.Conexao;
import br.com.api.route.PacienteRoutes;
import br.com.api.route.VacinaRoutes;
import br.com.api.route.ImunizacaoRoutes;
import br.com.api.route.EstatisticasRoutes;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Filter;

public class Main {

    public static void main(String[] args) {
        try {
            Connection conexao = Conexao.conectar();

            Spark.port(8080);

            Spark.options("/*", new Route() {
                @Override
                public Object handle(Request requisicaoHttp, Response respostaHttp) throws Exception {
                    String accessControlRequestHeaders = requisicaoHttp.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null)
                        respostaHttp.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

                    String accessControlRequestMethod = requisicaoHttp.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null)
                        respostaHttp.header("Access-Control-Allow-Methods", accessControlRequestMethod);

                    return "OK";
                }
            });

            Spark.before(new Filter() {
                @Override
                public void handle(Request requisicaoHttp, Response respostaHttp) throws Exception {
                    respostaHttp.header("Access-Control-Allow-Origin", "*");
                    respostaHttp.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
                    respostaHttp.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
                }
            });

            PacienteRoutes.configurarRotas();
            VacinaRoutes.configurarRotas();
            ImunizacaoRoutes.configurarRotas();
            EstatisticasRoutes.configurarRotas();

            System.out.println("🚀 Servidor rodando em http://localhost:8080");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
