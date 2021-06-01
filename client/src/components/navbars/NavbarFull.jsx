import React from 'react'

import {Link} from 'react-router-dom'

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
                <Link to="/"><img src="./img/logo.png" alt="Logo de Anytime"/></Link>
                <a id="lista" onClick={toggleList()}><i className="fas fa-list-ul" id="icon"></i></a>
            </div>
            <nav id="nav" className="invisible">
                <Link to="/">Inicio</Link>
                {props.token.authToken === undefined ? <Link to="/login">Login</Link> : <Link to="/app">Acceder</Link>}
                <Link to="contacto">Contacto</Link>
                <a id="sun" style={{display: "none"}} onClick={changeLight(this)}><i className="far fa-sun"></i></a>
                <a id="moon" onClick={changeDark(this)}><i className="far fa-moon"></i></a>
            </nav>
        </header>
    )
}

export default NavbarFull
