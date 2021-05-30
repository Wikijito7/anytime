import React from 'react'

const NavbarFull = (props) => {

    const toggleList = () => {

    }

    const changeDark = () => {

    }

    const changeLight = () => {

    }

    return (
        <header>
            <div className="responsive">
                <a href="index.html"><img src="./img/logo.png" alt="Logo de Anytime"/></a>
                <a id="lista" onClick={toggleList()}><i className="fas fa-list-ul" id="icon"></i></a>
            </div>
            <nav id="nav" className="invisible">
                <a href="index.html">Inicio</a>
                {props.token === undefined ? <a href="login.html">Login</a> : <a href="login.html">Acceder</a>}
                <a href="contacto.html">Contacto</a>
                <a id="sun" style={{display: "none"}} onClick={changeLight(this)}><i className="far fa-sun"></i></a>
                <a id="moon" onClick={changeDark(this)}><i className="far fa-moon"></i></a>
            </nav>
        </header>
    )
}

export default NavbarFull
