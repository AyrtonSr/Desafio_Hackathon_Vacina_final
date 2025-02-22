package br.com.api.dao;

import br.com.api.config.Conexao;
import br.com.api.model.Vacina;
import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class VacinaDAO {

    private String converteEnumParaString(List<Vacina.PublicoAlvo> publicoAlvo) {
        return String.join(",", publicoAlvo.stream().map(Enum::name).toArray(String[]::new));
    }

    private List<Vacina.PublicoAlvo> converteStringParaEnum(String publicoAlvoStr) {
        List<Vacina.PublicoAlvo> lista = new ArrayList<>();
        if (publicoAlvoStr != null) {
            for (String valor : publicoAlvoStr.split(",")) {
                String valorFormatado = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");

                lista.add(Vacina.PublicoAlvo.valueOf(valorFormatado.toUpperCase()));
            }
        }
        return lista;
    }

    public void inserir(Vacina vacina) {
        String sql = "INSERT INTO vacina (vacina, descricao, limite_aplicacao, publico_alvo) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vacina.getNome());
            stmt.setString(2, vacina.getDescricao());
            stmt.setObject(3, vacina.getLimiteIdadeMeses() != null ? vacina.getLimiteIdadeMeses() : null);
            stmt.setString(4, converteEnumParaString(vacina.getPublicoAlvo()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    vacina.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vacina> buscarPorFaixaEtaria(String faixa) {
        String sql = "SELECT * FROM vacina WHERE publico_alvo LIKE ?";
        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + faixa + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vacina vacina = new Vacina(
                            rs.getInt("id"),
                            rs.getString("vacina"),
                            rs.getString("descricao"),
                            rs.getObject("limite_aplicacao", Integer.class),
                            converteStringParaEnum(rs.getString("publico_alvo"))
                    );
                    vacinas.add(vacina);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacinas;
    }

    public List<Vacina> buscarPorIdadeMaior(int meses) {
        String sql = "SELECT * FROM vacina WHERE limite_aplicacao IS NULL OR limite_aplicacao > ?";
        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, meses);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vacina vacina = new Vacina(
                            rs.getInt("id"),
                            rs.getString("vacina"),
                            rs.getString("descricao"),
                            rs.getObject("limite_aplicacao", Integer.class),
                            converteStringParaEnum(rs.getString("publico_alvo"))
                    );
                    vacinas.add(vacina);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacinas;
    }

    public void atualizar(Vacina vacina) {
        String sql = "UPDATE vacina SET vacina = ?, descricao = ?, limite_aplicacao = ?, publico_alvo = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vacina.getNome());
            stmt.setString(2, vacina.getDescricao());
            stmt.setObject(3, vacina.getLimiteIdadeMeses() != null ? vacina.getLimiteIdadeMeses() : null);
            stmt.setString(4, converteEnumParaString(vacina.getPublicoAlvo()));
            stmt.setInt(5, vacina.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM vacina WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vacina buscarPorId(int id) {
        String sql = "SELECT * FROM vacina WHERE id = ?";
        Vacina vacina = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vacina = new Vacina(
                            rs.getInt("id"),
                            rs.getString("vacina"),
                            rs.getString("descricao"),
                            rs.getObject("limite_aplicacao", Integer.class),
                            converteStringParaEnum(rs.getString("publico_alvo"))
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacina;
    }

    public List<Vacina> listarTodos() {
        String sql = "SELECT * FROM vacina";
        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vacina vacina = new Vacina(
                        rs.getInt("id"),
                        rs.getString("vacina"),
                        rs.getString("descricao"),
                        rs.getObject("limite_aplicacao", Integer.class),
                        converteStringParaEnum(rs.getString("publico_alvo"))
                );
                vacinas.add(vacina);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacinas;
    }

}
