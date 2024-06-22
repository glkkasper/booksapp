import logo from './logo.svg';
import './App.css';

import React, { useEffect, useState } from 'react';
import axios from 'axios';
// Defina a URL base da API
const API_URL = process.env.QUARKUS_APP_API_URL || 'http://localhost:8081';

function App() {
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({ titulo: '', autor: '', anoPublicacao: '', numeroDePaginas: '' });
  const [editingBook, setEditingBook] = useState(null);
  const [errors, setErrors] = useState({});
  const [editErrors, setEditErrors] = useState({});

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get(`${API_URL}/books`);
      setBooks(response.data);
    } catch (error) {
      console.error('Erro ao buscar livros:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewBook({ ...newBook, [name]: value });
    setErrors({ ...errors, [name]: '' });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditingBook({ ...editingBook, [name]: value });
    setEditErrors({ ...editErrors, [name]: '' });
  };

  const validate = (book) => {
    let errors = {};
    if (!book.titulo) {
      errors.titulo = 'O título é obrigatório';
    }
    if (!book.autor) {
      errors.autor = 'O autor é obrigatório';
    }
    if (!book.anoPublicacao) {
      errors.anoPublicacao = 'O ano de publicação é obrigatório';
    } else if (isNaN(book.anoPublicacao) || book.anoPublicacao <= 0) {
      errors.anoPublicacao = 'O ano de publicação deve ser um número positivo';
    }
    if (!book.numeroDePaginas) {
      errors.numeroDePaginas = 'O numero de paginas é obrigatório';
    } else if (isNaN(book.numeroDePaginas) || book.numeroDePaginas <= 0) {
      errors.numeroDePaginas = 'O numero de paginas deve ser um número positivo';
    }
    return errors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validate(newBook);
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    try {
      await axios.post(`${API_URL}/books`, newBook);
      setNewBook({ titulo: '', autor: '', anoPublicacao: '', numeroDePaginas: ''});
      fetchBooks();
    } catch (error) {
      console.error('Erro ao adicionar livro:', error);
    }
  };

  const handleUpdate = async (id) => {
    const validationErrors = validate(editingBook);
    if (Object.keys(validationErrors).length > 0) {
      setEditErrors(validationErrors);
      return;
    }
    try {
      await axios.put(`${API_URL}/books/${id}`, editingBook);
      setEditingBook(null);
      fetchBooks();
    } catch (error) {
      console.error('Erro ao atualizar livro:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/books/${id}`);
      fetchBooks();
    } catch (error) {
      console.error('Erro ao deletar livro:', error);
    }
  };

  return (
    <div className="App">
      <div className="navbar">
        <h1>Book Management</h1>
      </div>
      <div className="container">
        <h1>Books</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="titulo"
            value={newBook.titulo}
            onChange={handleInputChange}
            placeholder="Título"
            required
          />
          {errors.titulo && <div className="error">{errors.titulo}</div>}
          <input
            type="text"
            name="autor"
            value={newBook.autor}
            onChange={handleInputChange}
            placeholder="Autor"
            required
          />
          {errors.autor && <div className="error">{errors.autor}</div>}
          <input
            type="number"
            name="anoPublicacao"
            value={newBook.anoPublicacao}
            onChange={handleInputChange}
            placeholder="Ano de Publicação"
            required
          />
          {errors.anoPublicacao && <div className="error">{errors.anoPublicacao}</div>}
          <input
            type="number"
            name="numeroDePaginas"
            value={newBook.numeroDePaginas}
            onChange={handleInputChange}
            placeholder="Numero De Paginas"
            required
          />
          {errors.numeroDePaginas && <div className="error">{errors.numeroDePaginas}</div>}
          <button type="submit">Adicionar Livro</button>     
        </form>
        <div className="book-list">
          {books.map(book => (
            <div className="book-item" key={book.id}>
              {editingBook && editingBook.id === book.id ? (
                <div>
                  <input
                    type="text"
                    name="titulo"
                    value={editingBook.titulo}
                    onChange={handleEditChange}
                    placeholder="Título"
                    required
                  />
                  {editErrors.titulo && <div className="error">{editErrors.titulo}</div>}
                  <input
                    type="text"
                    name="autor"
                    value={editingBook.autor}
                    onChange={handleEditChange}
                    placeholder="Autor"
                    required
                  />
                  {editErrors.autor && <div className="error">{editErrors.autor}</div>}
                  <input
                    type="number"
                    name="anoPublicacao"
                    value={editingBook.anoPublicacao}
                    onChange={handleEditChange}
                    placeholder="Ano de Publicação"
                    required
                  />
                  {editErrors.anoPublicacao && <div className="error">{editErrors.anoPublicacao}</div>}
                  <input
                    type="number"
                    name="numeroDePaginas"
                    value={newBook.numeroDePaginas}
                    onChange={handleInputChange}
                    placeholder="Numero De Paginas"
                    required
                  />
                  {editErrors.numeroDePaginas && <div className="error">{editErrors.numeroDePaginas}</div>}
                  <button onClick={() => handleUpdate(book.id)}>Salvar</button>
                  <button onClick={() => setEditingBook(null)}>Cancelar</button>
                </div>
              ) : (
                <div>
                  <h2>{book.titulo}</h2>
                  <p>Autor: {book.autor}</p>
                  <p>Ano de Publicação: {book.anoPublicacao}</p>
                  <p>Numero De Paginas: {book.numeroDePaginas}</p>
                  <button onClick={() => setEditingBook(book)}>Editar</button>
                  <button onClick={() => handleDelete(book.id)}>Deletar</button>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;