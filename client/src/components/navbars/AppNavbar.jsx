import React from 'react'

const AppNavbar = () => {
    return (
        <header id="header-app">
            <a href="#" id="admin"><i className="fas fa-cogs"></i></a>
            <div id="imagen-app">
                <a href="index.html"><img src="./img/logo.png" alt="Logo de Anytime"/></a>
            </div>
            <a href="perfil.html" className="perfil"><img id="profile" src="./img/profile.webp" alt="Imagen del usuario"/></a>
        </header>
    )
}

export default AppNavbar
