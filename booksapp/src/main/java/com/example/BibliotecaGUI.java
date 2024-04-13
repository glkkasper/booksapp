package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BibliotecaGUI extends JFrame {
    private JTextField txtTitulo, txtAutor, txtAnoPublicacao, txtId;
    private JButton btnInserir, btnConsultar, btnAtualizar, btnDeletar;
    private JTextArea txtAreaResultados;

    public BibliotecaGUI() {
        super("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new FlowLayout());

        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtAnoPublicacao = new JTextField(20);
        txtId = new JTextField(20);
        txtAreaResultados = new JTextArea(10, 40);
        txtAreaResultados.setEditable(false);

        btnInserir = new JButton("Inserir");
        btnConsultar = new JButton("Consultar");
        btnAtualizar = new JButton("Atualizar");
        btnDeletar = new JButton("Deletar");

        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Autor:"));
        add(txtAutor);
        add(new JLabel("Ano de Publicação:"));
        add(txtAnoPublicacao);
        add(new JLabel("ID (para atualizar/deletar):"));
        add(txtId);
        add(btnInserir);
        add(btnConsultar);
        add(btnAtualizar);
        add(btnDeletar);
        add(new JScrollPane(txtAreaResultados));

        btnInserir.addActionListener(e -> inserirLivro());
        btnConsultar.addActionListener(e -> consultarLivros());
        btnAtualizar.addActionListener(e -> atualizarLivro());
        btnDeletar.addActionListener(e -> deletarLivro());

        setVisible(true);
    }

    private Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/biblioteca";
        String user = "root";
        String password = "minhasenha";
        return DriverManager.getConnection(url, user, password);
    }

    private void inserirLivro() {
        try (Connection connection = conectar();
             Statement statement = connection.createStatement()) {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText());
            App.InserirLivro(statement, titulo, autor, anoPublicacao);
            txtAreaResultados.setText("Livro inserido com sucesso!");
        } catch (SQLException ex) {
            txtAreaResultados.setText("Erro ao inserir livro: " + ex.getMessage());
        }
    }

    private void consultarLivros() {
        try (Connection connection = conectar();
             Statement statement = connection.createStatement()) {
            ResultSet resultados = App.ConsultarLivros(statement);
            StringBuilder sb = new StringBuilder();
            while (resultados.next()) {
                sb.append("ID: ").append(resultados.getInt("id")).append(", Título: ").append(resultados.getString("titulo")).append(", Autor: ").append(resultados.getString("autor")).append(", Ano de Publicação: ").append(resultados.getInt("ano_publicacao")).append("\n");
            }
            txtAreaResultados.setText(sb.toString());
        } catch (SQLException ex) {
            txtAreaResultados.setText("Erro ao consultar livros: " + ex.getMessage());
        }
    }

    private void atualizarLivro() {
        try (Connection connection = conectar();
             Statement statement = connection.createStatement()) {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText());
            int id = Integer.parseInt(txtId.getText());
            App.AtualizarLivro(statement, titulo, autor, anoPublicacao, id);
            txtAreaResultados.setText("Livro atualizado com sucesso!");
        } catch (SQLException ex) {
            txtAreaResultados.setText("Erro ao atualizar livro: " + ex.getMessage());
        }
    }

    private void deletarLivro() {
        try (Connection connection = conectar();
             Statement statement = connection.createStatement()) {
            int id = Integer.parseInt(txtId.getText());
            App.DeletarLivro(statement, id);
            txtAreaResultados.setText("Livro deletado com sucesso!");
        } catch (SQLException ex) {
            txtAreaResultados.setText("Erro ao deletar livro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BibliotecaGUI::new);
    }
}

