import React from 'react'

const AppNavbar = (props) => {
    const token = props.token;

    return (
        <header id="header-app">
            <a href="/admin" id="admin"><i className="fas fa-cogs"></i></a>
            <div id="imagen-app">
                <a href="/"><img src="./img/logo.png" alt="Logo de Anytime"/></a>
            </div>
            <a href="/perfil" className="perfil"><img id="profile" src="./img/profile.webp"
                                                      alt="Imagen del usuario"/></a>
        </header>
    )
}

export default AppNavbar
