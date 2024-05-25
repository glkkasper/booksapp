import logo from './logo.svg';
import './App.css';

import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({ titulo: '', autor: '', anoPublicacao: '' });
  const [editingBook, setEditingBook] = useState(null);

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('/books');
      setBooks(response.data);
    } catch (error) {
      console.error('Erro ao buscar livros:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewBook({ ...newBook, [name]: value });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditingBook({ ...editingBook, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/books', newBook);
      setNewBook({ titulo: '', autor: '', anoPublicacao: '' });
      fetchBooks();
    } catch (error) {
      console.error('Erro ao adicionar livro:', error);
    }
  };

  const handleUpdate = async (id) => {
    try {
      await axios.put(`/books/${id}`, editingBook);
      setEditingBook(null);
      fetchBooks();
    } catch (error) {
      console.error('Erro ao atualizar livro:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`/books/${id}`);
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
          <input
            type="text"
            name="autor"
            value={newBook.autor}
            onChange={handleInputChange}
            placeholder="Autor"
            required
          />
          <input
            type="number"
            name="anoPublicacao"
            value={newBook.anoPublicacao}
            onChange={handleInputChange}
            placeholder="Ano de Publicação"
            required
          />
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
                  <input
                    type="text"
                    name="autor"
                    value={editingBook.autor}
                    onChange={handleEditChange}
                    placeholder="Autor"
                    required
                  />
                  <input
                    type="number"
                    name="anoPublicacao"
                    value={editingBook.anoPublicacao}
                    onChange={handleEditChange}
                    placeholder="Ano de Publicação"
                    required
                  />
                  <button onClick={() => handleUpdate(book.id)}>Salvar</button>
                  <button onClick={() => setEditingBook(null)}>Cancelar</button>
                </div>
              ) : (
                <div>
                  <h2>{book.titulo}</h2>
                  <p>Autor: {book.autor}</p>
                  <p>Ano de Publicação: {book.anoPublicacao}</p>
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