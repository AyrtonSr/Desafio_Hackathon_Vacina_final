package br.com.api.dao;

import br.com.api.model.Imunizacao;
import br.com.api.model.Paciente;

import java.util.Date;
import java.util.List;

public class TesteImunizacaoDAO {
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        ImunizacaoDAO imunizacaoDAO = new ImunizacaoDAO();

        // Criando um paciente primeiro
        Paciente novoPaciente = new Paciente(1, "Marcos", "98765432122", 'F', new Date());
        pacienteDAO.inserir(novoPaciente);
        System.out.println("Paciente inserido com ID: " + novoPaciente.getId());

        // Criando uma nova imunização associada ao paciente criado
        Imunizacao novaImunizacao = new Imunizacao(0, new Date(), "Pfizer", "Lote123", "Posto de Saúde", "Dra. Maria", novoPaciente.getId(), 1);
        imunizacaoDAO.inserir(novaImunizacao);
        System.out.println("Imunização inserida com ID: " + novaImunizacao.getId());

        // Listando todas as imunizações
        List<Imunizacao> imunizacoes = imunizacaoDAO.listarTodas();
        System.out.println("Imunizações cadastradas:");
        for (Imunizacao i : imunizacoes) {
            System.out.println(i.getId() + " - Paciente ID: " + i.getIdPaciente() + " - Dose ID: " + i.getIdDose());
        }

        // Excluindo a imunização
        //imunizacaoDAO.deletar(novaImunizacao.getId());
        //System.out.println("Imunização deletada!");
    }
}
