package br.com.api.dao;

import br.com.api.config.Conexao;
import br.com.api.model.Dose;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoseDAO {

    public void inserir(Dose dose) {
        String sql = "INSERT INTO dose (id_vacina, dose, idade_recomendada_aplicacao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, dose.getIdVacina());
            stmt.setString(2, dose.getDose());
            stmt.setInt(3, dose.getIdadeRecomendadaAplicacao());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    dose.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Dose> listarPorVacina(int idVacina) {
        String sql = "SELECT * FROM dose WHERE id_vacina = ?";
        List<Dose> doses = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVacina);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doses.add(new Dose(
                            rs.getInt("id"),
                            rs.getInt("id_vacina"),
                            rs.getString("dose"),
                            rs.getInt("idade_recomendada_aplicacao")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doses;
    }

}
