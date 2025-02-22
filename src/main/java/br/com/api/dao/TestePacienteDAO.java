package br.com.api.dao;

import br.com.api.model.Paciente;
import java.util.Date;
import java.util.List;

public class TestePacienteDAO {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();

        // Criando um novo paciente
        Paciente novoPaciente = new Paciente(0, "João da Silva", "12345678900", 'M', new Date());
        pacienteDAO.inserir(novoPaciente);
        System.out.println("Paciente inserido com ID: " + novoPaciente.getId());

        // Buscando o paciente inserido
        Paciente paciente = pacienteDAO.buscarPorId(novoPaciente.getId());
        if (paciente != null) {
            System.out.println("Paciente encontrado: " + paciente.getNome());
        }

        // Listando todos os pacientes
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        System.out.println("Pacientes cadastrados:");
        for (Paciente p : pacientes) {
            System.out.println(p.getId() + " - " + p.getNome());
        }

        // Atualizando o paciente
        paciente.setNome("João da Silva Atualizado");
        pacienteDAO.atualizar(paciente);
        System.out.println("Paciente atualizado!");

        // Excluindo o paciente
        pacienteDAO.deletar(paciente.getId());
        System.out.println("Paciente deletado!");
    }
}
