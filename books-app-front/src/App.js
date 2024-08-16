import logo from './logo.svg';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Livros from './Livros';
import Autores from './Autores';
import Home from './Home'; // Importando o novo componente Home

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/livros" element={<Livros />} />
        <Route path="/autores" element={<Autores />} />
      </Routes>
    </Router>
  );
}

export default App;