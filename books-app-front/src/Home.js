import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <div>
      <h1>Bem-vindo à aplicação de livros!</h1>
      <p>Esta é a página inicial da aplicação. Use os links abaixo para navegar.</p>
      <nav>
        <ul>
          <li>
            <Link to="/livros">Livros</Link>
          </li>
          <li>
            <Link to="/autores">Autores</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default Home;