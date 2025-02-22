package br.com.api.dao;

import br.com.api.config.Conexao;
import br.com.api.model.Imunizacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImunizacaoDAO {

    public void inserir(Imunizacao imunizacao) {
        String sql = "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, imunizacao.getIdPaciente());
            stmt.setInt(2, imunizacao.getIdDose());
            stmt.setDate(3, new java.sql.Date(imunizacao.getDataAplicacao().getTime()));
            stmt.setString(4, imunizacao.getFabricante());
            stmt.setString(5, imunizacao.getLote());
            stmt.setString(6, imunizacao.getLocalAplicacao());
            stmt.setString(7, imunizacao.getProfissionalAplicador());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    imunizacao.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Imunizacao imunizacao) {
        String sql = "UPDATE imunizacoes SET data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(imunizacao.getDataAplicacao().getTime()));
            stmt.setString(2, imunizacao.getFabricante());
            stmt.setString(3, imunizacao.getLote());
            stmt.setString(4, imunizacao.getLocalAplicacao());
            stmt.setString(5, imunizacao.getProfissionalAplicador());
            stmt.setInt(6, imunizacao.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM imunizacoes WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Imunizacao buscarPorId(int id) {
        String sql = "SELECT * FROM imunizacoes WHERE id = ?";
        Imunizacao imunizacao = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    imunizacao = new Imunizacao(
                            rs.getInt("id"),
                            rs.getDate("data_aplicacao"),
                            rs.getString("fabricante"),
                            rs.getString("lote"),
                            rs.getString("local_aplicacao"),
                            rs.getString("profissional_aplicador"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_dose")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imunizacao;
    }

    public List<Imunizacao> listarPorPaciente(int idPaciente) {
        String sql = "SELECT * FROM imunizacoes WHERE id_paciente = ?";
        List<Imunizacao> imunizacoes = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    imunizacoes.add(new Imunizacao(
                            rs.getInt("id"),
                            rs.getDate("data_aplicacao"),
                            rs.getString("fabricante"),
                            rs.getString("lote"),
                            rs.getString("local_aplicacao"),
                            rs.getString("profissional_aplicador"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_dose")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imunizacoes;
    }

    public List<Imunizacao> listarTodas() {
        String sql = "SELECT * FROM imunizacoes";
        List<Imunizacao> imunizacoes = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                imunizacoes.add(new Imunizacao(
                        rs.getInt("id"),
                        rs.getDate("data_aplicacao"),
                        rs.getString("fabricante"),
                        rs.getString("lote"),
                        rs.getString("local_aplicacao"),
                        rs.getString("profissional_aplicador"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_dose")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imunizacoes;
    }

    public void deletarPorPaciente(int idPaciente) {
        String sql = "DELETE FROM imunizacoes WHERE id_paciente = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
