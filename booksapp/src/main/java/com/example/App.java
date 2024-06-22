package com.example;

//importando as classes de conexao e excecao
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/biblioteca";
        String user = "root";
        String passowrd = "minhasenha";
        try (Connection conexao = DriverManager.getConnection(url, user, passowrd)) {
            System.out.println("Conexao bem sucedida");
            Statement comando = conexao.createStatement();
            /* System.out.println(InserirLivro(comando, "RedHat", "Tulio", 2024)); */
            ConsultarLivros(comando);
            AtualizarLivro(comando, "Revolucao dos bichos", "Gorge", 2018, 2, 03);
            ConsultarLivros(comando);
            DeletarLivro(comando, 1);
            ConsultarLivros(comando);
            conexao.close();
        } catch (SQLException e) { // tratar excecoes
            System.out.println("Excessao SQL");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Excessao Generica");
            e.printStackTrace();
        }
    }

    public static String InserirLivro(Statement statement, String titulo, String autor, int ano_publicacao, int numeroDePaginas)
            throws SQLException {
        int retorno = statement.executeUpdate("INSERT INTO livros (titulo, autor, ano_publicacao, numero_de_paginas) VALUES ('" + titulo
                + "', '" + autor + "', " + ano_publicacao + numeroDePaginas + ");");
        if (retorno == 1) {
            return "Livro inserido com sucesso";

        }
        return "Erro ao inserir livro";
    }

    public static String ConsultarLivros(Statement statement) throws SQLException {
        ResultSet resultados = statement.executeQuery("SELECT * FROM livros;");
        String resultadosFormatados = "";
        while (resultados.next()) {
            resultadosFormatados = resultadosFormatados + "ID: " + resultados.getInt("id") + ", Título: "
                    + resultados.getString("titulo") + ", Autor: " + resultados.getString("autor")
                    + ", Ano de Publicação: " + resultados.getInt("ano_publicacao") + " Numero De Paginas: " + resultados.getInt("numero_de_paginas") + "\n";

        }
        return resultadosFormatados;
    }

    public static String ConsultarLivro(Statement statement, String titulo, String autor, String ano_publicacao, String id, String numeroDePaginas)
            throws SQLException {
        String consulta = "SELECT * FROM livros WHERE 1=1";
        if (!titulo.isEmpty()) {
            consulta = consulta + " AND titulo = '" + titulo + "'";
        }
        if (!autor.isEmpty()) {
            consulta = consulta + " AND autor = '" + autor + "'";
        }
        if (!ano_publicacao.isEmpty()) {
            consulta = consulta + " AND ano_publicacao = " + ano_publicacao;
        }
        if (!id.isEmpty()) {
            consulta = consulta + " AND id = " + id;
        }
        if (!numeroDePaginas.isEmpty()) {
            consulta = consulta + " AND numero_de_paginas = " + numeroDePaginas;    
            
        }
        ResultSet resultados = statement.executeQuery(consulta);
        String resultadosFormatados = "";
        while (resultados.next()) {
            resultadosFormatados = resultadosFormatados + "ID: " + resultados.getInt("id") + ", Título: "
                    + resultados.getString("titulo") + ", Autor: " + resultados.getString("autor")
                    + ", Ano de Publicação: " + resultados.getInt("ano_publicacao") + " Numero De Paginas: " + resultados.getInt("numero_de_paginas") + "\n";

        }
        if (resultadosFormatados.isEmpty()) {
            resultadosFormatados = "Sua busca nao retornou resultados!";

        }
        return resultadosFormatados;
    }

    public static String AtualizarLivro(Statement statement, String titulo, String autor, int ano_publicacao, int id, int numeroDePaginas)
            throws SQLException {
        int retorno = statement.executeUpdate("UPDATE livros SET titulo='" + titulo + "', autor='" + autor
                + "', ano_publicacao=" + ano_publicacao + "', numero_de_paginas=" + numeroDePaginas + "' WHERE id=" + id);
        if (retorno == 1) {
            return "Livro atualizado com sucesso";

        }
        return "Erro ao atualizar livro";
    }

    public static String DeletarLivro(Statement statement, int id) throws SQLException {
        int retorno = statement.executeUpdate("DELETE FROM livros WHERE id=" + id);
        if (retorno == 1) {
            return "Livro deletado com sucesso";

        }
        return "Erro ao deletar livro";
    }
}
