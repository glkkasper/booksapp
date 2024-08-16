import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Autores.css';

const API_URL = process.env.QUARKUS_APP_API_URL || 'http://localhost:8081';

function Autores() {
  const [autores, setAutores] = useState([]);
  const [newAutor, setNewAutor] = useState({ nome: '', dataNascimento: '' });
  const [editingAutor, setEditingAutor] = useState(null);
  const [errors, setErrors] = useState({});
  const [editErrors, setEditErrors] = useState({});

  useEffect(() => {
    fetchAutores();
  }, []);

  const fetchAutores = async () => {
    try {
      const response = await axios.get(`${API_URL}/autores`);
      setAutores(response.data);
    } catch (error) {
      console.error('Erro ao buscar autores:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewAutor({ ...newAutor, [name]: value });
    setErrors({ ...errors, [name]: '' });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditingAutor({ ...editingAutor, [name]: value });
    setEditErrors({ ...editErrors, [name]: '' });
  };

  const validate = (autor) => {
    let errors = {};
    if (!autor.nome) {
      errors.nome = 'O nome é obrigatório';
    }
    if (!autor.dataNascimento) {
      errors.dataNascimento = 'A data de nascimento é obrigatória';
    }
    return errors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validate(newAutor);
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    try {
      await axios.post(`${API_URL}/autores`, newAutor);
      setNewAutor({ nome: '', dataNascimento: '' });
      fetchAutores();
    } catch (error) {
      console.error('Erro ao adicionar autor:', error);
    }
  };

  const handleUpdate = async (id) => {
    const validationErrors = validate(editingAutor);
    if (Object.keys(validationErrors).length > 0) {
      setEditErrors(validationErrors);
      return;
    }
    try {
      await axios.put(`${API_URL}/autores/${id}`, editingAutor);
      setEditingAutor(null);
      fetchAutores();
    } catch (error) {
      console.error('Erro ao atualizar autor:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/autores/${id}`);
      fetchAutores();
    } catch (error) {
      console.error('Erro ao deletar autor:', error);
    }
  };

  return (
    <div className="Autores">
      <div className="navbar">
        <h1>Gerenciamento de Autores</h1>
      </div>
      <div className="container">
        <h1>Autores</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="nome"
            value={newAutor.nome}
            onChange={handleInputChange}
            placeholder="Nome"
            required
          />
          {errors.nome && <div className="error">{errors.nome}</div>}
          <input
            type="date"
            name="dataNascimento"
            value={newAutor.dataNascimento}
            onChange={handleInputChange}
            placeholder="Data de Nascimento"
            required
          />
          {errors.dataNascimento && <div className="error">{errors.dataNascimento}</div>}
          <button type="submit">Adicionar Autor</button>
        </form>
        <div className="autor-list">
          {autores.map(autor => (
            <div className="autor-item" key={autor.id}>
              {editingAutor && editingAutor.id === autor.id ? (
                <div>
                  <input
                    type="text"
                    name="nome"
                    value={editingAutor.nome}
                    onChange={handleEditChange}
                    placeholder="Nome"
                    required
                  />
                  {editErrors.nome && <div className="error">{editErrors.nome}</div>}
                  <input
                    type="date"
                    name="dataNascimento"
                    value={editingAutor.dataNascimento}
                    onChange={handleEditChange}
                    placeholder="Data de Nascimento"
                    required
                  />
                  {editErrors.dataNascimento && <div className="error">{editErrors.dataNascimento}</div>}
                  <button onClick={() => handleUpdate(autor.id)}>Salvar</button>
                  <button onClick={() => setEditingAutor(null)}>Cancelar</button>
                </div>
              ) : (
                <div>
                  <h2>{autor.nome}</h2>
                  <p>Data de Nascimento: {autor.dataNascimento}</p>
                  <button onClick={() => setEditingAutor(autor)}>Editar</button>
                  <button onClick={() => handleDelete(autor.id)}>Deletar</button>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Autores;