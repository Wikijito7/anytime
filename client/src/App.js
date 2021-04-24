import React from 'react';
import Footer from './components/Footer';
import Index from './components/Index';
import NavbarFull from './components/navbars/NavbarFull'

// TODO: Comprobar las cookies para saber si está logueado y pasarselo a la página que se encuentra para temas como
// el dark mode y que acceda directamente a la app en vez de pasar por el Login.

function App() {
  return (
    <div className="App">
      <NavbarFull/>
      <Index/>
      <Footer/>
    </div>
  );
}

export default App;
