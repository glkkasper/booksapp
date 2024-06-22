package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BibliotecaGUI extends JFrame {
    private JTextField txtTitulo, txtAutor, txtAnoPublicacao, txtId, txtnumeroDePaginas;
    private JButton btnInserir, btnConsultar, btnAtualizar, btnDeletar;
    private JTextArea txtAreaResultados;

    public BibliotecaGUI() {
        super("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Labels
        constraints.gridx = 0; // primeira coluna
        constraints.gridy = 0; // primeira linha
        add(new JLabel("Título:"), constraints);

        constraints.gridy++; // próxima linha
        add(new JLabel("Autor:"), constraints);

        constraints.gridy++; // próxima linha
        add(new JLabel("Ano de Publicação:"), constraints);

        constraints.gridy++; // próxima linha
        add(new JLabel("ID (para atualizar/deletar):"), constraints);

        constraints.gridy++; // próxima linha
        add(new JLabel("Numero de paginas:"), constraints);

        // Text Fields
        constraints.gridx = 1; // segunda coluna
        constraints.gridy = 0; // primeira linha
        txtTitulo = new JTextField(25);
        add(txtTitulo, constraints);

        constraints.gridy++; // próxima linha
        txtAutor = new JTextField(25);
        add(txtAutor, constraints);

        constraints.gridy++; // próxima linha
        txtAnoPublicacao = new JTextField(25);
        add(txtAnoPublicacao, constraints);

        constraints.gridy++; // próxima linha
        txtId = new JTextField(25);
        add(txtId, constraints);

        constraints.gridy++; // próxima linha
        txtnumeroDePaginas = new JTextField(25);
        add(txtnumeroDePaginas, constraints);

        // Buttons
        constraints.gridx = 0; // primeira coluna
        constraints.gridwidth = 5; // ocupa duas colunas
        constraints.gridy++; // próxima linha
        btnInserir = new JButton("Inserir");
        add(btnInserir, constraints);

        constraints.gridy++; // próxima linha
        btnConsultar = new JButton("Consultar");
        add(btnConsultar, constraints);

        constraints.gridy++; // próxima linha
        btnAtualizar = new JButton("Atualizar");
        add(btnAtualizar, constraints);

        constraints.gridy++; // próxima linha
        btnDeletar = new JButton("Deletar");
        add(btnDeletar, constraints);

        // Result Area
        constraints.gridy++; // próxima linha
        txtAreaResultados = new JTextArea(10, 20);
        txtAreaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResultados);
        constraints.ipady = 15; // torna a área de texto um pouco maior
        add(scrollPane, constraints);

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
        if (validarCampos() == true) {

            try (Connection connection = conectar();
                    Statement statement = connection.createStatement()) {
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                int anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText());
                int numeroDePaginas = Integer.parseInt(txtnumeroDePaginas.getText());
                App.InserirLivro(statement, titulo, autor, anoPublicacao, numeroDePaginas);
                txtAreaResultados.setText("Livro inserido com sucesso!");
                connection.close();
                limparCampos();
            } catch (SQLException ex) {
                txtAreaResultados.setText("Erro ao inserir livro");
            }
        } else {
            txtAreaResultados.setText("Por favor preencha todos os campos!");
        }
    }

    private void consultarLivros() {
        try (Connection connection = conectar();
                Statement statement = connection.createStatement()) {
            String resultados = "";
            if (validarCamposConsulta() == true) {
                resultados = App.ConsultarLivro(statement, txtTitulo.getText(), txtAutor.getText(),
                        txtAnoPublicacao.getText(), txtId.getText(), txtnumeroDePaginas.getText());
                limparCampos();
            } else {
                resultados = App.ConsultarLivros(statement);
            }
            txtAreaResultados.setText(resultados);
            connection.close();
        } catch (SQLException ex) { 
            txtAreaResultados.setText("Erro ao consultar livros");
        }
    }

    private void atualizarLivro() {
        if (validarCampos() == true) {

            try (Connection connection = conectar();
                    Statement statement = connection.createStatement()) {
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                int anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText());
                int id = Integer.parseInt(txtId.getText());
                int numeroDePaginas = Integer.parseInt(txtnumeroDePaginas.getText());
                App.AtualizarLivro(statement, titulo, autor, anoPublicacao, id, numeroDePaginas);
                txtAreaResultados.setText("Livro atualizado com sucesso!");
                connection.close();
                limparCampos();
            } catch (SQLException ex) {
                txtAreaResultados.setText("Erro ao atualizar livro");
            }
        } else {
            txtAreaResultados.setText("Por favor preencha todos os campos!");
        }
    }

    private void deletarLivro() {
        if (validarCampos() == true) {

            try (Connection connection = conectar();
                    Statement statement = connection.createStatement()) {
                int id = Integer.parseInt(txtId.getText());
                App.DeletarLivro(statement, id);
                txtAreaResultados.setText("Livro deletado com sucesso!");
                connection.close();
                limparCampos();
            } catch (SQLException ex) {
                txtAreaResultados.setText("Erro ao deletar livro");
            }
        } else {
            txtAreaResultados.setText("Por favor preencha todos os campos!");
        }
    }

    private void limparCampos() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnoPublicacao.setText("");
        txtId.setText("");
        txtnumeroDePaginas.setText("");
    }

    private boolean validarCampos() {

        Boolean resultado = false;
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String anoPublicacao = txtAnoPublicacao.getText();
        String numeroDePaginas = txtnumeroDePaginas.getText();

        if (!titulo.isEmpty() && !autor.isEmpty() && !anoPublicacao.isEmpty() && !numeroDePaginas.isEmpty()) {
            resultado = true;
        }
        return resultado;
    }

    private boolean validarCamposConsulta() {

        Boolean resultado = false;
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String anoPublicacao = txtAnoPublicacao.getText();
        String id = txtId.getText();
        String numeroDePaginas = txtnumeroDePaginas.getText();

        if (!titulo.isEmpty() || !autor.isEmpty() || !anoPublicacao.isEmpty() || !id.isEmpty() || !numeroDePaginas.isEmpty()) {
            resultado = true;
        }
        return resultado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BibliotecaGUI::new);
    }

}
